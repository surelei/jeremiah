package com.jeremiahxu.leisure.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jeremiahxu.leisure.po.RoleProfile;
import com.jeremiahxu.leisure.repository.BaseRepository;
import com.jeremiahxu.leisure.service.RoleService;

/**
 * 角色信息服务实现类
 * 
 * @author Jeremiah Xu
 * 
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {
	@Resource(name = "repo")
	private BaseRepository<RoleProfile, Integer> roleRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeremiahxu.learyperi.user.service.RoleService#loadAllRole()
	 */
	@Override
	public List<RoleProfile> findAllRoles() {
		return this.roleRepo.queryAll(RoleProfile.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeremiahxu.learyperi.user.service.RoleService#findRole(int)
	 */
	@Override
	public RoleProfile findRole(int id) {
		return this.roleRepo.loadById(RoleProfile.class, id);
	}

	@Override
	public List<RoleProfile> findPagedRoles(int pageNumber, int numberPerPage) {
		String jpql = "select r from RoleProfile r order by r.code";
		return roleRepo.query(jpql, pageNumber, numberPerPage);
	}

	public BaseRepository<RoleProfile, Integer> getRoleRepo() {
		return roleRepo;
	}

	public void setRoleRepo(BaseRepository<RoleProfile, Integer> roleRepo) {
		this.roleRepo = roleRepo;
	}

}
