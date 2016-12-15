package com.jeremiahxu.surelei.po;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

/**
 * 组织结构实体对象
 * 
 * @author Jeremiah Xu
 * 
 */
public class OrgProfile implements Serializable, Comparable<OrgProfile> {
	private static final long serialVersionUID = 1L;
	private Integer id;// 唯一标识
	private String name = "";// 组织机构名称
	private int level = 0;// 组织机构在树形结构中所处的层级
	private int order = 0;// 组织机构显示的顺序
	private String description = "";// 组织机构描述
	private String idPath = "";// 组织机构的ID全路径，例如：/123/124/323/
	private String namePath = "";// 组织机构的名称全路径，例如：/root_org/总公司/分公司/
	private OrgProfile parent;// 所属上级组织机构
	private List<OrgProfile> children = new ArrayList<OrgProfile>();// 下级组织机构列表

	@Transactional
	public void save() {
		this.computePathAndLevel();
	}

	@Transactional
	public void update() {
		this.computePathAndLevel();
	}

	@Transactional
	public void remove() {
	}

	/**
	 * compute path and level.
	 */
	private void computePathAndLevel() {
		String idPath = this.getId() + "/";
		String namePath = this.getName() + "/";
		int level = 0;
		OrgProfile parent = this.getParent();
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
			for (OrgProfile child : this.getChildren()) {
				child.computePathAndLevel();
			}
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public OrgProfile getParent() {
		return parent;
	}

	public void setParent(OrgProfile parent) {
		this.parent = parent;
	}

	public List<OrgProfile> getChildren() {
		if (children != null) {
			Collections.sort(children);
		}
		return children;
	}

	public void setChildren(List<OrgProfile> children) {
		for (OrgProfile org : children) {
			org.setParent(this);
		}
		this.children = children;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
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


	@Override
	public int compareTo(OrgProfile o) {
		return this.order - o.getOrder();
	}
}
