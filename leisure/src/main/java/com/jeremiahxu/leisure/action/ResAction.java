package com.jeremiahxu.leisure.action;

import java.util.List;

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
import com.jeremiahxu.leisure.service.ResService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * resource action.
 * 
 * @author Jeremiah Xu
 * 
 */
@Controller
@ParentPackage("struts-leisure")
@Namespace("/res")
@Scope("request")
public class ResAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	@Resource(name = "resService")
	private ResService resService;
	private ResProfile res;
	private List<ResProfile> resList;

	/**
	 * 显示所有资源信息。
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "list", results = { @Result(type = "freemarker", location = "/page/res/resList.html") })
	public String listRole() throws Exception {
		this.setResList(this.getResService().findAllResource());
		log.debug("list resource.");
		return SUCCESS;
	}

	/**
	 * 创建资源信息。
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "create", results = { @Result(type = "redirect", location = "/res/list.htm") })
	public String createRole() throws Exception {
		this.getRes().save();
		log.debug("create resource.");
		return SUCCESS;
	}

	/**
	 * 修改资源信息。
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "update", results = { @Result(type = "redirect", location = "/res/list.htm") })
	public String updateRole() throws Exception {
		this.getRes().update();
		log.debug("update resource.");
		return SUCCESS;
	}

	/**
	 * 删除资源信息。
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "delete", results = { @Result(type = "redirect", location = "/res/list.htm") })
	public String deleteRole() throws Exception {
		this.getRes().remove();
		log.debug("delete resource.");
		return SUCCESS;
	}

	/**
	 * 去创建资源页面。
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "new", results = { @Result(type = "freemarker", location = "/page/res/resNew.html") })
	public String toNew() throws Exception {
		log.debug("to new resource.");
		return SUCCESS;
	}

	/**
	 * 去修改资源信息页面。
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "edit", results = { @Result(type = "freemarker", location = "/page/res/resEdit.html") })
	public String toEdit() throws Exception {
		this.setRes(this.getResService().findResource(this.getRes().getId()));
		log.debug("to edit role");
		return SUCCESS;
	}

	public ResService getResService() {
		return resService;
	}

	public void setResService(ResService resService) {
		this.resService = resService;
	}

	public ResProfile getRes() {
		return res;
	}

	public void setRes(ResProfile res) {
		this.res = res;
	}

	public List<ResProfile> getResList() {
		return resList;
	}

	public void setResList(List<ResProfile> resList) {
		this.resList = resList;
	}

}
