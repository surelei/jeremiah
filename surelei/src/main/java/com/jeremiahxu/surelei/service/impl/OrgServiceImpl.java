package com.jeremiahxu.surelei.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.jeremiahxu.surelei.po.OrgProfile;
import com.jeremiahxu.surelei.service.OrgService;

/**
 * 组织机构信息服务实现类
 * 
 * @author Jeremiah Xu
 * 
 */
public class OrgServiceImpl implements OrgService {

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
		return null;
	}

	public OrgProfile findOrg(int id) {
		return null;
	}

}
