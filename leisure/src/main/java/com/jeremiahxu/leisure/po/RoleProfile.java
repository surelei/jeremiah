package com.jeremiahxu.leisure.po;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.transaction.annotation.Transactional;

import com.jeremiahxu.leisure.repository.impl.PersistantObject;

/**
 * 用户角色实体对象
 * 
 * @author Jeremiah Xu
 * 
 */
@Entity
@Table(name = "T_ROLE_INFO")
public class RoleProfile extends PersistantObject implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "ROI_ID", length = 10)
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Integer id;// 唯一标识
	@Column(name = "ROI_CODE", length = 50, nullable = false, unique = true)
	private String code;// 角色代码
	@Column(name = "ROI_NAME", length = 50, nullable = false)
	private String name;// 角色名称
	@Column(name = "ROI_DESC", length = 100, nullable = false)
	private String description;// 角色描述
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH }, targetEntity = UserProfile.class)
	@JoinTable(name = "T_USER_ROLE_REL", joinColumns = { @JoinColumn(name = "ROI_ID") }, inverseJoinColumns = { @JoinColumn(name = "USI_ID") })
	private Set<UserProfile> users;// 角色所包括的用户
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH }, targetEntity = ResProfile.class)
	@JoinTable(name = "T_ROLE_RES_REL", joinColumns = { @JoinColumn(name = "ROI_ID") }, inverseJoinColumns = { @JoinColumn(name = "RSI_ID") })
	private Set<ResProfile> resources;// 角色可以访问的资源列表
	private boolean checked = false;
	
	public void addResource(ResProfile res) {
		this.getResources().add(res);
	}

	public void addUser(UserProfile user) {
		this.getUsers().add(user);
	}

	@Transactional
	public void save() {
		super.save();
	}

	@Transactional
	public void update() {
		super.update();
	}

	@Transactional
	public void remove() {
		super.remove();
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean getChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}
