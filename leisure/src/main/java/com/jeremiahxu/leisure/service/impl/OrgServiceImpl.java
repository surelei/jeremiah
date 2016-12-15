package com.jeremiahxu.leisure.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jeremiahxu.leisure.po.OrgProfile;
import com.jeremiahxu.leisure.repository.BaseRepository;
import com.jeremiahxu.leisure.service.OrgService;

/**
 * 组织机构信息服务实现类
 * 
 * @author Jeremiah Xu
 * 
 */
@Service("orgService")
public class OrgServiceImpl implements OrgService {
	@Resource(name = "repo")
	private BaseRepository<OrgProfile, Integer> orgRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeremiahxu.learyperi.user.service.OrgService#findRoot()
	 */
	@Override
	public OrgProfile findRoot() {
		String jpql = "select o from OrgProfile o where o.code=:code and o.level=:level";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("code", "root_org");
		params.put("level", 0);
		return this.getOrgRepo().load(jpql, params);
	}

	public BaseRepository<OrgProfile, Integer> getOrgRepo() {
		return orgRepo;
	}

	public void setOrgRepo(BaseRepository<OrgProfile, Integer> orgRepo) {
		this.orgRepo = orgRepo;
	}

	public OrgProfile findOrg(int id) {
		return this.getOrgRepo().loadById(OrgProfile.class, id);
	}

}
