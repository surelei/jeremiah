package com.jeremiahxu.leisure.po;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.springframework.transaction.annotation.Transactional;

import com.jeremiahxu.leisure.repository.impl.PersistantObject;

/**
 * menu item
 * 
 * @author Jeremiah Xu
 * 
 */
@Entity
@Table(name = "T_MENU_INFO")
public class MenuProfile extends PersistantObject implements Serializable, Comparable<MenuProfile> {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "MI_ID", length = 10)
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Integer id;
	@Column(name = "MI_CODE", length = 20, nullable = false, unique = true)
	private String code = "";// 代码
	@Column(name = "MI_NAME", length = 40, nullable = false)
	private String name = "";// 名称
	@Column(name = "MI_LEVEL", length = 2, nullable = false)
	private int level = 0;// 层级
	@Column(name = "MI_ORDER", length = 5, nullable = false)
	private int order = 0;// 显示顺序，从小到大显示。
	@Column(name = "MI_TYPE", length = 2, nullable = false)
	private int type = 0;// 展示类型，0为在主窗体内打开子页面，1为全页面刷新跳转，2为弹出新窗体，99为无效果。
	@Column(name = "MI_URL", length = 250, nullable = false)
	private String url = "";// 要访问的URL
	@Column(name = "MI_IMG_PATH", length = 250, nullable = false)
	private String imagePath = "";// 菜单上显示的图片
	@Column(name = "MI_ID_PATH", length = 250, nullable = false)
	private String idPath = "";// id的全路径，例如：/123/321/345/
	@Column(name = "MI_NAME_PATH", length = 250, nullable = false)
	private String namePath = "";// 名称的全路径，例如：/root/系统管理/菜单管理/
	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL }, targetEntity = MenuProfile.class, mappedBy = "parent")
	@OrderBy("order ASC")
	private List<MenuProfile> children = new ArrayList<MenuProfile>();// 子节点列表
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "MI_PARENT_ID")
	private MenuProfile parent;// 父节点

	/**
	 * save in order to get the menu's id. compute the path and the level by id. update menu's infomation.
	 */
	@Transactional
	public void save() {
		super.save();
		this.computePathAndLevel();
		super.update();
	}

	/**
	 * compute the path and the level ,then update the menu's information.
	 */
	@Transactional
	public void update() {
		this.computePathAndLevel();
		super.update();
	}

	/**
	 * remove menu information.
	 */
	@Transactional
	public void remove() {
		super.remove();
	}

	/**
	 * compute idpath、namepath and level.
	 * 
	 */
	private void computePathAndLevel() {
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

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
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
