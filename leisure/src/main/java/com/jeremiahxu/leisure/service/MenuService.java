package com.jeremiahxu.leisure.service;

import com.jeremiahxu.leisure.po.MenuProfile;

/**
 * 对菜单的操作的服务接口
 * 
 * @author Jeremiah Xu
 * 
 */
public interface MenuService {

	/**
	 * 根据id取得菜单项。
	 * 
	 * @param id
	 * @return
	 */
	public MenuProfile findMenu(int id);

	/**
	 * 取得菜单跟节点。
	 * 
	 * @return
	 */
	public MenuProfile findRoot();

}
