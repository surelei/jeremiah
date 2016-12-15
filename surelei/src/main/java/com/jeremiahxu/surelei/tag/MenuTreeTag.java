package com.jeremiahxu.surelei.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jeremiahxu.surelei.exception.MenuException;
import com.jeremiahxu.surelei.po.MenuProfile;
import com.jeremiahxu.surelei.service.MenuService;
import com.jeremiahxu.surelei.util.ContextUtil;
import com.jeremiahxu.surelei.util.MenuType;

/**
 * 展示菜单树的标签
 * 
 * @author Jeremiah Xu
 * 
 */
public class MenuTreeTag extends SimpleTagSupport {
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	private MenuProfile menu;
	private String clazz;
	private String id = "menu";

	@Override
	public void doTag() throws JspException, IOException {
		if (this.menu == null) {// 如果标签的menu属性没有值则读取数据库取得菜单树
			MenuService menuService = (MenuService) ContextUtil.getBean("menuService");
			this.setMenu(menuService.findRoot());
		}
		if (this.getMenu() == null) {
			throw new MenuException("No menu data exists.");
		}
		// 生成第一层菜单
		List<MenuProfile> menuList = this.getMenu().getChildren();
		StringBuffer html = new StringBuffer();
		html.append("<ul id=\"").append(this.id).append("\">");
		for (MenuProfile menuItem : menuList) {
			if (this.clazz == null) {
				html.append("<li>");
			} else {
				html.append("<li class=\"").append(clazz).append("\">");
			}

			html.append("<a id=\"").append(menuItem.getId()).append("\"");
			switch (menuItem.getType()) {
			case MenuType.OPEN_INNER_PAGE:// 在主窗体内打开子页面
				html.append(" name=\".").append(menuItem.getUrl()).append("\" href=\"#\">");
				break;
			case MenuType.OPEN_POPUP_PAGE:// 弹出窗体
				html.append(" name=\".").append(menuItem.getUrl()).append("\" target=\"_blank\" href=\"#\">");
				break;
			case MenuType.WHOLE_PAGE_FORWARD:// 整个页面刷新
				html.append(" href=\".").append(menuItem.getUrl()).append("\">");
				break;
			case MenuType.NO_OPERATION:// 不做任何操作
				html.append(" href=\"#\">");
				break;
			default:
			}
			html.append(menuItem.getName()).append("</a>");
			// 递归生成菜单
			html.append(this.writeHTML(menuItem));
			html.append("</li>");
		}
		html.append("</ul>");
		log.debug(html.toString());
		try {
			getJspContext().getOut().print(html);
		} catch (Exception ex) {
			log.error("menutree tag error.", ex);
		}
	}

	/**
	 * 递归菜单生成HTML
	 * 
	 * @param menu
	 * @return
	 */
	private String writeHTML(MenuProfile menu) {
		List<MenuProfile> menuList = menu.getChildren();
		if (menuList.size() == 0) {
			return "";
		}
		StringBuffer html = new StringBuffer();
		html.append("<ul>");
		for (MenuProfile menuItem : menuList) {
			html.append("<li>");
			html.append("<a id=\"").append(menuItem.getId()).append("\"");
			switch (menuItem.getType()) {
			case MenuType.OPEN_INNER_PAGE:// 在主窗体内打开子页面
				html.append(" name=\".").append(menuItem.getUrl()).append("\" href=\"#\">");
				break;
			case MenuType.OPEN_POPUP_PAGE:// 弹出窗体
				html.append(" name=\".").append(menuItem.getUrl()).append("\" target=\"_blank\" href=\"#\">");
				break;
			case MenuType.WHOLE_PAGE_FORWARD:// 整个页面刷新
				html.append(" href=\".").append(menuItem.getUrl()).append("\">");
				break;
			case MenuType.NO_OPERATION:// 不做任何操作
				html.append(" href=\"#\">");
				break;
			default:
			}
			html.append(menuItem.getName()).append("</a>");
			html.append(this.writeHTML(menuItem));
			html.append("</li>");
		}
		html.append("</ul>");
		return html.toString();
	}

	public MenuProfile getMenu() {
		return menu;
	}

	public void setMenu(MenuProfile menu) {
		this.menu = menu;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
