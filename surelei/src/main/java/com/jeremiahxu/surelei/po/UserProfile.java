package com.jeremiahxu.surelei.po;

import java.io.Serializable;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

/**
 * 用户实体对象
 * 
 * @author Jeremiah Xu
 * 
 */
public class UserProfile implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;// 唯一标识
	private String name;// 唯一用户名
	private String password;// 密码
	private String firstName;// 用户的名字
	private String lastName;// 用户的姓
	private Set<RoleProfile> roles;// 用户所属的角色列表
	private OrgProfile org;// 用户所属组织机构

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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Set<RoleProfile> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleProfile> roles) {
		this.roles = roles;
	}

	public OrgProfile getOrg() {
		return org;
	}

	public void setOrg(OrgProfile org) {
		this.org = org;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
