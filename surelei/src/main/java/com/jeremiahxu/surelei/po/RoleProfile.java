package com.jeremiahxu.surelei.po;

import java.io.Serializable;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

/**
 * 用户角色实体对象
 * 
 * @author Jeremiah Xu
 * 
 */
public class RoleProfile implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;// 唯一标识
	private String name;// 角色名称
	private String description;// 角色描述
	private Set<UserProfile> users;// 角色所包括的用户
	private Set<ResProfile> resources;// 角色可以访问的资源列表

	public void addResource(ResProfile res) {
		this.getResources().add(res);
	}

	public void addUser(UserProfile user) {
		this.getUsers().add(user);
	}

	@Transactional
	public void save() {
	}

	@Transactional
	public void update() {
	}

	@Transactional
	public void remove() {
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

	public Set<ResProfile> getResources() {
		return resources;
	}

	public void setResources(Set<ResProfile> resources) {
		this.resources = resources;
	}

	public Set<UserProfile> getUsers() {
		return users;
	}

	public void setUsers(Set<UserProfile> users) {
		this.users = users;
	}

}
