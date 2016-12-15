package com.jeremiahxu.surelei.service;

import com.jeremiahxu.surelei.po.OrgProfile;

/**
 * 组织机构信息服务类
 * 
 * @author Jeremiah Xu
 * 
 */
public interface OrgService {

    /**
     * 根据id取得组织机构信息
     * 
     * @param id
     * @return
     */
    public OrgProfile findOrg(int id);

    /**
     * 取得组织机构根节点
     * 
     * @return
     */
    public OrgProfile findRoot();
}
