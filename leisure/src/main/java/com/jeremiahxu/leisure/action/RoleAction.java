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

import com.jeremiahxu.leisure.po.ResProfile;
import com.jeremiahxu.leisure.po.RoleProfile;
import com.jeremiahxu.leisure.po.UserProfile;
import com.jeremiahxu.leisure.service.ResService;
import com.jeremiahxu.leisure.service.RoleService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * organization action
 * 
 * @author Jeremiah Xu
 * 
 */
@Controller
@ParentPackage("struts-leisure")
@Namespace("/role")
@Scope("request")
public class RoleAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	@Resource(name = "roleService")
	private RoleService roleService;
	@Resource(name = "resService")
	private ResService resService;
	private RoleProfile role;
	private List<UserProfile> userList;
	private List<ResProfile> resourceList;
	private List<RoleProfile> roleList;
	private String ids;

	/**
	 * 查询角色信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "list", results = { @Result(type = "freemarker", location = "/page/role/roleList.html") })
	public String listRole() throws Exception {
		this.setRoleList(this.getRoleService().findAllRoles());
		log.debug("list role.");
		return SUCCESS;
	}

	@Action(value = "listMin", results = { @Result(type = "freemarker", location = "/page/role/roleListMin.html") })
	public String listMinRole() throws Exception {
		this.setRoleList(this.getRoleService().findAllRoles());
		for (RoleProfile role : this.getRoleList()) {
			String id = "|" + String.valueOf(role.getId()) + "|";
			if (this.getIds().indexOf(id) >= 0) {
				role.setChecked(true);
			}
		}
		log.debug("list role min.");
		return SUCCESS;
	}

	/**
	 * 创建角色
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "create", results = { @Result(type = "redirect", location = "/role/list.htm") })
	public String createRole() throws Exception {
		Set<ResProfile> resources = new HashSet<ResProfile>();
		for (ResProfile res : this.getRole().getResources()) {
			resources.add(this.getResService().findResource(res.getId()));
		}
		this.getRole().setResources(resources);
		this.getRole().save();
		log.debug("create role");
		return SUCCESS;
	}

	/**
	 * 修改角色
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "update", results = { @Result(type = "redirect", location = "/role/list.htm") })
	public String updateRole() throws Exception {
		Set<ResProfile> resources = new HashSet<ResProfile>();
		for (ResProfile res : this.getRole().getResources()) {
			resources.add(this.getResService().findResource(res.getId()));
		}
		this.getRole().setResources(resources);
		this.getRole().update();
		log.debug("update role");
		return SUCCESS;
	}

	/**
	 * 删除角色
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "delete", results = { @Result(type = "redirect", location = "/role/list.htm") })
	public String deleteRole() throws Exception {
		this.getRole().remove();
		log.debug("delete role");
		return SUCCESS;
	}

	/**
	 * 显示角色的用户信息。
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "showUsers", results = { @Result(type = "freemarker", location = "/page/role/userListMin.html") })
	public String showUsers() throws Exception {
		RoleProfile role = this.getRoleService().findRole(this.getRole().getId());
		this.setUserList(new ArrayList<>(role.getUsers()));
		log.debug("show role's users.");
		return SUCCESS;
	}

	/**
	 * 显示角色的资源信息。
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "showResources", results = { @Result(type = "freemarker", location = "/page/role/resListMin.html") })
	public String showResources() throws Exception {
		RoleProfile role = this.getRoleService().findRole(this.getRole().getId());
		this.setResourceList(new ArrayList<ResProfile>(role.getResources()));
		log.debug("show role's resources.");
		return SUCCESS;
	}

	/**
	 * 跳转去新建角色页面。
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "new", results = { @Result(type = "freemarker", location = "/page/role/roleEdit.html") })
	public String toNew() throws Exception {
		log.debug("to new role");
		return SUCCESS;
	}

	/**
	 * 跳转去编辑页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "edit", results = { @Result(type = "freemarker", location = "/page/role/roleEdit.html") })
	public String toEdit() throws Exception {
		this.setRole(this.getRoleService().findRole(this.getRole().getId()));
		log.debug("to edit role");
		return SUCCESS;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public RoleProfile getRole() {
		return role;
	}

	public void setRole(RoleProfile role) {
		this.role = role;
	}

	public List<RoleProfile> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<RoleProfile> roleList) {
		this.roleList = roleList;
	}

	public ResService getResService() {
		return resService;
	}

	public void setResService(ResService resService) {
		this.resService = resService;
	}

	public List<UserProfile> getUserList() {
		return userList;
	}

	public void setUserList(List<UserProfile> userList) {
		this.userList = userList;
	}

	public List<ResProfile> getResourceList() {
		return resourceList;
	}

	public void setResourceList(List<ResProfile> resourceList) {
		this.resourceList = resourceList;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

}
