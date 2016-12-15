package com.jeremiahxu.leisure.po;

import java.util.Set;

public class RoleBuilder {
	private RoleProfile role;

	private RoleBuilder() {
		role = new RoleProfile();
	}

	public static RoleBuilder aRole() {
		return new RoleBuilder();
	}

	public RoleProfile build() {
		return role;
	}

	public RoleBuilder withCode(String code) {
		this.role.setCode(code);
		return this;
	}

	public RoleBuilder withName(String name) {
		this.role.setName(name);
		return this;
	}

	public RoleBuilder withDescription(String description) {
		this.role.setDescription(description);
		return this;
	}

	public RoleBuilder withId(int id) {
		this.role.setId(id);
		return this;
	}

	public RoleBuilder withUsers(Set<UserProfile> users) {
		this.role.setUsers(users);
		return this;
	}

	public RoleBuilder withResource(Set<ResProfile> resources) {
		this.role.setResources(resources);
		return this;
	}
}
