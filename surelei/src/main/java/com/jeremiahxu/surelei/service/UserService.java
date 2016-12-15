package com.jeremiahxu.surelei.service;

import java.util.List;

import com.jeremiahxu.surelei.po.UserProfile;

/**
 * 用户信息管理服务接口
 * 
 * @author Jeremiah Xu
 * 
 */
public interface UserService {
	/**
	 * 根据用户名取得用户信息
	 * 
	 * @param name
	 * @return
	 */
	public UserProfile findUserByName(String name);

	/**
	 * 根据用户名和密码取得用户信息
	 * 
	 * @param name
	 * @param password
	 * @return
	 */
	public UserProfile findUserByNameAndPassword(String name, String password);

	/**
	 * 取得所有用户信息
	 * 
	 * @return
	 */
	public List<UserProfile> findAllUser();

	/**
	 * 根据用户id取得用户信息
	 * 
	 * @param id
	 * @return
	 */
	public UserProfile findUser(int id);

}
