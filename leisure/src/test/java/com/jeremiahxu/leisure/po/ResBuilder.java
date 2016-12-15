package com.jeremiahxu.leisure.po;

import java.util.Set;

public class ResBuilder {
	private ResProfile res;

	private ResBuilder() {
		res = new ResProfile();
	}

	public static ResBuilder aRes() {
		return new ResBuilder();
	}

	public ResProfile build() {
		return res;
	}

	public ResBuilder withName(String name) {
		this.res.setName(name);
		return this;
	}

	public ResBuilder withUrl(String url) {
		this.res.setUrl(url);
		return this;
	}

	public ResBuilder withId(int id) {
		this.res.setId(id);
		return this;
	}

	public ResBuilder withRoles(Set<RoleProfile> roles) {
		this.res.setRoles(roles);
		return this;
	}

}
