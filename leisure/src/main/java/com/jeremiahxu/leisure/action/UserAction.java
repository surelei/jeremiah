package com.jeremiahxu.leisure.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jeremiahxu.leisure.po.RoleProfile;
import com.jeremiahxu.leisure.po.UserProfile;
import com.jeremiahxu.leisure.service.OrgService;
import com.jeremiahxu.leisure.service.RoleService;
import com.jeremiahxu.leisure.service.UserService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * resource action.
 * 
 * @author Jeremiah Xu
 * 
 */
@Controller
@ParentPackage("struts-leisure")
@Namespace("/user")
@Scope("request")
public class UserAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "roleService")
	private RoleService roleService;
	@Resource(name = "orgService")
	private OrgService orgService;
	private UserProfile user;
	private List<UserProfile> userList;
	private List<RoleProfile> roleList;

	/**
	 * 显示所有资源信息。
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "list", results = { @Result(type = "freemarker", location = "/page/user/userList.html") })
	public String listUser() throws Exception {
		this.setUserList(this.getUserService().findAllUser());
		log.debug("list users.");
		return SUCCESS;
	}

	/**
	 * 创建资源信息。
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "create", results = { @Result(type = "redirect", location = "/user/list.htm") })
	public String createRole() throws Exception {
		Set<RoleProfile> roles = new HashSet<RoleProfile>();
		for (RoleProfile role : this.getUser().getRoles()) {
			roles.add(this.getRoleService().findRole(role.getId()));
		}
		this.getUser().setRoles(roles);
		this.getUser().setOrg(this.getOrgService().findOrg(this.getUser().getOrg().getId()));
		this.getUser().save();
		log.debug("create user.");
		return SUCCESS;
	}

	/**
	 * 修改资源信息。
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "update", results = { @Result(type = "redirect", location = "/user/list.htm") })
	public String updateRole() throws Exception {
		Set<RoleProfile> roles = new HashSet<RoleProfile>();
		for (RoleProfile role : this.getUser().getRoles()) {
			roles.add(this.getRoleService().findRole(role.getId()));
		}
		this.getUser().setRoles(roles);
		this.getUser().setOrg(this.getOrgService().findOrg(this.getUser().getOrg().getId()));
		this.getUser().update();
		log.debug("update user.");
		return SUCCESS;
	}

	/**
	 * 删除资源信息。
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "delete", results = { @Result(type = "redirect", location = "/user/list.htm") })
	public String deleteRole() throws Exception {
		this.getUser().remove();
		log.debug("delete user.");
		return SUCCESS;
	}

	/**
	 * 去创建资源页面。
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "new", results = { @Result(type = "freemarker", location = "/page/user/userNew.html") })
	public String toNew() throws Exception {
		log.debug("to new user.");
		return SUCCESS;
	}

	/**
	 * 去修改资源信息页面。
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "edit", results = { @Result(type = "freemarker", location = "/page/user/userEdit.html") })
	public String toEdit() throws Exception {
		this.setUser(this.getUserService().findUser(this.getUser().getId()));
		log.debug("to edit user.");
		return SUCCESS;
	}

	/**
	 * 取得所属角色。
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "showRoles", results = { @Result(type = "freemarker", location = "/page/role/roleListMin.html") })
	public String showRoles() throws Exception {
		Set<RoleProfile> set = this.getUserService().findUser(this.getUser().getId()).getRoles();
		this.setRoleList(new ArrayList<RoleProfile>(set));
		return SUCCESS;
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

	public List<UserProfile> getUserList() {
		return userList;
	}

	public void setUserList(List<UserProfile> userList) {
		this.userList = userList;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public OrgService getOrgService() {
		return orgService;
	}

	public void setOrgService(OrgService orgService) {
		this.orgService = orgService;
	}

	public List<RoleProfile> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<RoleProfile> roleList) {
		this.roleList = roleList;
	}

}
