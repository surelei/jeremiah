package com.jeremiahxu.surelei.po;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * menu item
 * 
 * @author Jeremiah Xu
 * 
 */
public class MenuProfile implements Serializable, Comparable<MenuProfile> {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String code = "";// 代码
	private String name = "";// 名称
	private int level = 0;// 层级
	private int order = 0;// 显示顺序，从小到大显示。
	private int type = 0;// 展示类型，0为在主窗体内打开子页面，1为全页面刷新跳转，2为弹出新窗体，99为无效果。
	private String url = "";// 要访问的URL
	private String idPath = "";// id的全路径，例如：/123/321/345/
	private String namePath = "";// 名称的全路径，例如：/root/系统管理/菜单管理/
	private List<MenuProfile> children = new ArrayList<MenuProfile>();// 子节点列表
	private MenuProfile parent;// 父节点

	/**
	 * 复制制定的菜单数据。
	 * 
	 * @param menu
	 */
	public void copy(MenuProfile menu) {
		this.setId(menu.getId());
		this.setCode(menu.getCode());
		this.setName(menu.getName());
		this.setLevel(menu.getLevel());
		this.setOrder(menu.getOrder());
		this.setType(menu.getType());
		this.setUrl(menu.getUrl());
		this.setIdPath(menu.getIdPath());
		this.setNamePath(menu.getNamePath());
		this.setChildren(menu.getChildren());
		this.setParent(menu.getParent());
	}

	/**
	 * compute idpath、namepath and level.
	 * 
	 */
	public void computePathAndLevel() {
		String idPath = this.getId() + "/";
		String namePath = this.getName() + "/";
		int level = 0;
		MenuProfile parent = this.getParent();
		if (parent == null) {
			idPath = "/" + idPath;
			namePath = "/" + namePath;
			level = 0;
		} else {
			idPath = parent.getIdPath() + idPath;
			namePath = parent.getNamePath() + namePath;
			level = parent.getLevel() + 1;
		}
		this.setIdPath(idPath);
		this.setNamePath(namePath);
		this.setLevel(level);
		if (this.getChildren() != null) {
			for (MenuProfile child : this.getChildren()) {
				child.computePathAndLevel();
			}
		}
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIdPath() {
		return idPath;
	}

	public void setIdPath(String idPath) {
		this.idPath = idPath;
	}

	public String getNamePath() {
		return namePath;
	}

	public void setNamePath(String namePath) {
		this.namePath = namePath;
	}

	/**
	 * 取得子菜单时进行排序
	 * 
	 * @return
	 */
	public List<MenuProfile> getChildren() {
		if (children != null) {
			Collections.sort(children);
		}
		return children;
	}

	public void setChildren(List<MenuProfile> children) {
		for (MenuProfile menu : children) {
			menu.setParent(this);
		}
		this.children = children;
	}

	public MenuProfile getParent() {
		return parent;
	}

	public void setParent(MenuProfile parent) {
		this.parent = parent;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public int compareTo(MenuProfile o) {
		return this.order - o.getOrder();
	}

	public Integer getId() {
		return id;
	}

}
