package com.jeremiahxu.leisure.service;

import java.util.List;

import com.jeremiahxu.leisure.po.ResProfile;

/**
 * 资源服务接口。
 * 
 * @author Jeremiah Xu
 * 
 */
public interface ResService {
	/**
	 * 取得所有资源
	 * 
	 * @return
	 */
	public List<ResProfile> findAllResource();

	/**
	 * 根据资源id取得资源信息
	 * 
	 * @param id
	 * @return
	 */
	public ResProfile findResource(int id);

}
