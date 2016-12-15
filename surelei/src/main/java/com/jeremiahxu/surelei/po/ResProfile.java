package com.jeremiahxu.surelei.po;

import java.io.Serializable;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

/**
 * 可操作资源对象，基本上就是可访问的URL。
 * 
 * @author Jeremiah Xu
 * 
 */
public class ResProfile implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;// 唯一标识
	private String name;// 资源名称
	private String url;// 可访问的方法
	private Set<RoleProfile> roles;// 可以访问该资源的角色列表

	@Transactional
	public void save() {
	}

	@Transactional
	public void update() {
	}

	@Transactional
	public void remove() {
	}

	public void addRole(RoleProfile role) {
		this.getRoles().add(role);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Set<RoleProfile> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleProfile> roles) {
		this.roles = roles;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
