package com.jeremiahxu.surelei.service.impl;

import java.util.List;

import com.jeremiahxu.surelei.po.RoleProfile;
import com.jeremiahxu.surelei.service.RoleService;

/**
 * 角色信息服务实现类
 * 
 * @author Jeremiah Xu
 * 
 */
public class RoleServiceImpl implements RoleService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeremiahxu.learyperi.user.service.RoleService#loadAllRole()
	 */
	@Override
	public List<RoleProfile> findAllRoles() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeremiahxu.learyperi.user.service.RoleService#findRole(int)
	 */
	@Override
	public RoleProfile findRole(int id) {
		return null;
	}

	@Override
	public List<RoleProfile> findPagedRoles(int pageNumber, int numberPerPage) {
		String jpql = "select r from RoleProfile r order by r.code";
		return null;
	}

}
