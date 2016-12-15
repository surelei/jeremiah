package com.jeremiahxu.surelei.service;

import com.jeremiahxu.surelei.po.MenuProfile;

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

	/**
	 * 创建一个菜单项。
	 * 
	 * @param menu
	 */
	public void createMenu(MenuProfile menu);

	/**
	 * 删除一个菜单项。
	 * 
	 * @param menu
	 */
	public void deleteMenu(MenuProfile menu);

	/**
	 * 更新一个菜单项。
	 * 
	 * @param menu
	 */
	public void updateMenu(MenuProfile menu);
}
