package com.jeremiahxu.leisure.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

import com.jeremiahxu.leisure.exception.LeisureException;
import com.jeremiahxu.leisure.po.MenuProfile;
import com.jeremiahxu.leisure.po.OrgProfile;
import com.jeremiahxu.leisure.po.ResProfile;
import com.jeremiahxu.leisure.po.RoleProfile;
import com.jeremiahxu.leisure.po.UserProfile;
import com.jeremiahxu.leisure.service.MenuService;
import com.jeremiahxu.leisure.service.OrgService;
import com.jeremiahxu.leisure.service.ResService;
import com.jeremiahxu.leisure.service.RoleService;
import com.jeremiahxu.leisure.service.UserService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 系统安装初始化的操作类
 * 
 * @author Jeremiah Xu
 * 
 */
@Controller
@ParentPackage("struts-leisure")
@Namespace("/config")
@Scope("request")
public class ConfigAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	public static String CONFIG_LOCATION = "initData.xml";
	@Resource(name = "resService")
	private ResService resService;
	@Resource(name = "orgService")
	private OrgService orgService;
	@Resource(name = "roleService")
	private RoleService roleService;
	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "menuService")
	private MenuService menuService;

	/**
	 * 初始化数据
	 * 
	 * @return
	 * @throws Exception
	 */
	@Transactional
	@Action(value = "init", results = { @Result(type = "freemarker", location = "/page/config/setup_success.html") })
	public String initData() throws Exception {
		// 判断是否有数据
		List<ResProfile> resList = this.resService.findAllResource();
		if (resList != null && resList.size() > 0) {
			throw new LeisureException("清空数据库后才可以初始化!");
		}
		List<RoleProfile> roleList = this.roleService.findAllRoles();
		if (roleList != null && roleList.size() > 0) {
			throw new LeisureException("清空数据库后才可以初始化!");
		}
		OrgProfile orgRoot = this.orgService.findRoot();
		if (orgRoot != null) {
			throw new LeisureException("清空数据库后才可以初始化!");
		}
		List<UserProfile> userList = this.userService.findAllUser();
		if (userList != null && userList.size() > 0) {
			throw new LeisureException("清空数据库后才可以初始化!");
		}
		MenuProfile menuRoot = this.menuService.findRoot();
		if (menuRoot != null) {
			throw new LeisureException("清空数据库后才可以初始化!");
		}
		ApplicationContext ctx = new ClassPathXmlApplicationContext(CONFIG_LOCATION);
		// 初始化菜单信息
		MenuProfile menu = (MenuProfile) ctx.getBean("menu_root");
		menu.save();
		log.info("menu init success.");
		// 初始化资源信息
		Map<String, ResProfile> resMap = ctx.getBeansOfType(ResProfile.class);
		for (ResProfile res : resMap.values()) {
			res.save();
		}
		log.info("resource init success.");
		// 初始化角色信息
		Map<String, RoleProfile> roleMap = ctx.getBeansOfType(RoleProfile.class);
		for (RoleProfile role : roleMap.values()) {
			role.save();
		}
		log.info("role init success.");
		// 初始化组织机构信息
		OrgProfile org = (OrgProfile) ctx.getBean("org_root");
		org.save();
		log.info("organization init success.");
		// 初始化用户信息
		Map<String, UserProfile> userMap = ctx.getBeansOfType(UserProfile.class);
		for (UserProfile user : userMap.values()) {
			user.save();
		}
		log.info("user init success.");
		// close context
		((ConfigurableApplicationContext) ctx).close();
		log.info("leisure init success.");
		return SUCCESS;
	}

	public ResService getResService() {
		return resService;
	}

	public void setResService(ResService resService) {
		this.resService = resService;
	}

	public OrgService getOrgService() {
		return orgService;
	}

	public void setOrgService(OrgService orgService) {
		this.orgService = orgService;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

}
