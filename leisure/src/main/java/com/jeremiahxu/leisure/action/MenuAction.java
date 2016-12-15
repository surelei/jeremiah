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
import com.jeremiahxu.leisure.service.MenuService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * menu action
 * 
 * @author Jeremiah Xu
 * 
 */
@Controller
@ParentPackage("struts-leisure")
@Namespace("/menu")
@Scope("request")
public class MenuAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	@Resource(name = "menuService")
	private MenuService menuService;
	private MenuProfile menu;

	/**
	 * 列出所有菜单
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "list", results = { @Result(type = "freemarker", location = "/page/menu/menuList.html") })
	public String listMenu() throws Exception {
		log.debug("list menu.");
		return SUCCESS;
	}

	/**
	 * 创建菜单
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "create", results = { @Result(type = "redirect", location = "/menu/list.htm") })
	public String createMenu() throws Exception {
		MenuProfile parent = this.getMenuService().findMenu(this.getMenu().getParent().getId());
		this.getMenu().setParent(parent);
		this.getMenu().save();
		log.debug("create menu.");
		return SUCCESS;
	}

	/**
	 * 修改菜单
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "update", results = { @Result(type = "redirect", location = "/menu/list.htm") })
	public String updateMenu() throws Exception {
		MenuProfile parent = this.getMenuService().findMenu(this.getMenu().getParent().getId());
		this.getMenu().setParent(parent);
		this.getMenu().update();
		log.debug("update menu.");
		return SUCCESS;
	}

	/**
	 * 删除菜单
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "delete", results = { @Result(type = "redirect", location = "/menu/list.htm") })
	public String deleteMenu() throws Exception {
		this.getMenu().remove();
		log.debug("delete menu.");
		return SUCCESS;
	}

	/**
	 * 显示菜单明细
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "show", results = { @Result(type = "freemarker", location = "/page/menu/menuDetail.html") })
	public String showMenu() throws Exception {
		this.setMenu(this.getMenuService().findMenu(this.getMenu().getId()));
		log.debug("show menu.");
		return SUCCESS;
	}

	/**
	 * 跳转去新建页面
	 * 
	 * @return
	 */
	@Action(value = "new", results = { @Result(type = "freemarker", location = "/page/menu/menuEdit.html") })
	public String toNew() {
		if ("root_menu".equals(this.getMenu().getParent().getCode())) {// 如果父菜单为根则属于新建一级菜单
			MenuProfile root = this.getMenuService().findRoot();
			this.getMenu().setParent(root);
		}
		log.debug("to new menu.");
		return SUCCESS;
	}

	/**
	 * 去菜单编辑页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "edit", results = { @Result(type = "freemarker", location = "/page/menu/menuEdit.html") })
	public String toEdit() throws Exception {
		this.setMenu(this.getMenuService().findMenu(this.getMenu().getId()));
		log.debug("to edit menu.");
		return SUCCESS;
	}

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	public MenuProfile getMenu() {
		return menu;
	}

	public void setMenu(MenuProfile menu) {
		this.menu = menu;
	}

}
