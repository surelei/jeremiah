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

import com.jeremiahxu.leisure.po.OrgProfile;
import com.jeremiahxu.leisure.service.OrgService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * organization action
 * 
 * @author Jeremiah Xu
 * 
 */
@Controller
@ParentPackage("struts-leisure")
@Namespace("/org")
@Scope("request")
public class OrgAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	@Resource(name = "orgService")
	private OrgService orgService;
	private OrgProfile org;

	/**
	 * 列出所有组织机构信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "list", results = { @Result(type = "freemarker", location = "/page/org/orgList.html") })
	public String listOrg() throws Exception {
		log.debug("list org.");
		return SUCCESS;
	}

	/**
	 * 列出所有组织机构到小窗口。
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "listMin", results = { @Result(type = "freemarker", location = "/page/org/orgListMin.html") })
	public String listOrgMin() throws Exception {
		log.debug("list org min.");
		return SUCCESS;
	}

	/**
	 * 创建机构
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "create", results = { @Result(type = "redirect", location = "/org/list.htm") })
	public String createOrg() throws Exception {
		OrgProfile parent = this.getOrgService().findOrg(this.getOrg().getParent().getId());
		this.getOrg().setParent(parent);
		this.getOrg().save();
		log.debug("create org.");
		return SUCCESS;
	}

	/**
	 * 修改机构
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "update", results = { @Result(type = "redirect", location = "/org/list.htm") })
	public String updateOrg() throws Exception {
		OrgProfile parent = this.getOrgService().findOrg(this.getOrg().getParent().getId());
		this.getOrg().setParent(parent);
		this.getOrg().update();
		log.debug("update org.");
		return SUCCESS;
	}

	/**
	 * 删除机构
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "delete", results = { @Result(type = "redirect", location = "/org/list.htm") })
	public String deleteOrg() throws Exception {
		this.getOrg().remove();
		log.debug("delete org.");
		return SUCCESS;
	}

	/**
	 * 显示机构明细
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "show", results = { @Result(type = "freemarker", location = "/page/org/orgDetail.html") })
	public String showOrg() throws Exception {
		this.setOrg(this.getOrgService().findOrg(this.getOrg().getId()));
		log.debug("show org.");
		return SUCCESS;
	}

	/**
	 * 跳转去新建页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "new", results = { @Result(type = "freemarker", location = "/page/org/orgEdit.html") })
	public String toNew() throws Exception {
		if ("root_org".equals(this.getOrg().getParent().getCode())) {// 如果父节点为根则为创建一级机构
			OrgProfile root = this.getOrgService().findRoot();
			this.getOrg().setParent(root);
		}
		log.debug("to new org");
		return SUCCESS;
	}

	/**
	 * 跳转去编辑页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "edit", results = { @Result(type = "freemarker", location = "/page/org/orgEdit.html") })
	public String toEdit() throws Exception {
		this.setOrg(this.getOrgService().findOrg(this.getOrg().getId()));
		log.debug("to edit org");
		return SUCCESS;
	}

	public OrgProfile getOrg() {
		return org;
	}

	public void setOrg(OrgProfile org) {
		this.org = org;
	}

	public OrgService getOrgService() {
		return orgService;
	}

	public void setOrgService(OrgService orgService) {
		this.orgService = orgService;
	}

}
