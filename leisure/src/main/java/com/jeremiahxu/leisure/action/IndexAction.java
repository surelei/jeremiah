package com.jeremiahxu.leisure.action;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jeremiahxu.leisure.po.MenuProfile;
import com.jeremiahxu.leisure.po.UserProfile;
import com.jeremiahxu.leisure.service.MenuService;
import com.jeremiahxu.leisure.service.UserService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 首页跳转
 * 
 * @author Jeremiah Xu
 * 
 */
@Controller
@ParentPackage("struts-leisure")
@Namespace("/index")
@Scope("request")
public class IndexAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	@Resource(name = "menuService")
	private MenuService menuService;
	@Resource(name = "userService")
	private UserService userService;
	private UserProfile user;
	private String type;// 进入登录页面的原因类型
	private String tip;// 在登录页面显示的提示信息

	/**
	 * 跳转去首页
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "home", results = { @Result(type = "freemarker", location = "/main.html"), @Result(name = "setup", type = "freemarker", location = "/page/config/setup.html") })
	public String home() throws Exception {
		MenuProfile menu = this.getMenuService().findRoot();
		if (menu == null) {
			log.info("to setup page.");
			return "setup";
		} else {
			log.info("to main page.");
			return SUCCESS;
		}
	}

	/**
	 * 跳转去登录页面。
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "login", results = { @Result(type = "freemarker", location = "/login.html") })
	public String toLogin() throws Exception {
		// 登录失败后进入登录页面的情况
		if ("login_failure".equals(type)) {
			this.setTip("用户名或密码错误！");
		} else if ("session_timeout".equals(type)) {
			this.setTip("操作超时，请重新登录！");
		} else {
			this.setTip("请输入用户名和密码！");
		}
		log.info("toLogin.");
		return SUCCESS;
	}

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserProfile getUser() {
		return user;
	}

	public void setUser(UserProfile user) {
		this.user = user;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

}
