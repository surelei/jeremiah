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
 * 可操作资源对象，基本上就是可访问的URL。
 * 
 * @author Jeremiah Xu
 * 
 */
@Entity
@Table(name = "T_RES_INFO")
public class ResProfile extends PersistantObject implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "RSI_ID", length = 10)
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Integer id;// 唯一标识
	@Column(name = "RSI_NAME", length = 50, nullable = false, unique = true)
	private String name;// 资源名称
	@Column(name = "RSI_URL", length = 200, nullable = false, unique = true)
	private String url;// 可访问的方法
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.REFRESH }, targetEntity = RoleProfile.class)
	@JoinTable(name = "T_ROLE_RES_REL", joinColumns = { @JoinColumn(name = "RSI_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROI_ID") })
	private Set<RoleProfile> roles;// 可以访问该资源的角色列表

	@Transactional
	@Override
	public void save() {
		super.save();
	}

	@Transactional
	@Override
	public void update() {
		super.update();
	}

	@Transactional
	@Override
	public void remove() {
		super.remove();
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
