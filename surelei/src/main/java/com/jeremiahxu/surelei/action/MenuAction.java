package com.jeremiahxu.surelei.action;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeremiahxu.surelei.po.MenuProfile;
import com.jeremiahxu.surelei.service.MenuService;

/**
 * menu action
 * 
 * @author Jeremiah Xu
 * 
 */
@Controller
@RequestMapping("/menu")
public class MenuAction {
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	@Resource(name = "menuService")
	private MenuService menuService;

	/**
	 * 列出所有菜单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String listMenu() throws Exception {
		log.debug("list menu.");
		return "page/menu/menuList";
	}

	/**
	 * 创建菜单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/create")
	public String createMenu(@ModelAttribute("menu") MenuProfile menu) throws Exception {
		this.menuService.createMenu(menu);
		log.debug("create menu.");
		return "page/menu/menuList";
	}

	/**
	 * 修改菜单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/update")
	public String updateMenu(@ModelAttribute("menu") MenuProfile menu) throws Exception {
		this.menuService.updateMenu(menu);
		log.debug("update menu.");
		return "page/menu/menuList";
	}

	/**
	 * 删除菜单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String deleteMenu(@ModelAttribute("menu") MenuProfile menu) throws Exception {
		this.menuService.deleteMenu(menu);
		log.debug("delete menu.");
		return "page/menu/menuList";
	}

	/**
	 * 显示菜单明细
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/show")
	public String showMenu(@ModelAttribute("menu") MenuProfile menu) throws Exception {
		menu.copy(this.menuService.findMenu(menu.getId()));
		menu.setParent(this.menuService.findMenu(menu.getParent().getId()));
		log.debug("show menu.");
		return "page/menu/menuDetail";
	}

	/**
	 * 跳转去新建页面
	 * 
	 * @return
	 */
	@RequestMapping("/new")
	public String toNew(@ModelAttribute("menu") MenuProfile menu) {
		if ("root_menu".equals(menu.getParent().getCode())) {// 如果父菜单为根则属于新建一级菜单
			menu.setParent(this.getMenuService().findRoot());
		}
		log.debug("to new menu.");
		return "page/menu/menuEdit";
	}

	/**
	 * 去菜单编辑页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/edit")
	public String toEdit(@ModelAttribute("menu") MenuProfile menu) throws Exception {
		menu.copy(this.menuService.findMenu(menu.getId()));
		menu.setParent(this.menuService.findMenu(menu.getParent().getId()));
		log.debug("to edit menu.");
		return "page/menu/menuEdit";
	}

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

}
