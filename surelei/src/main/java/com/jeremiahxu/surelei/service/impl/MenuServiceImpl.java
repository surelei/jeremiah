package com.jeremiahxu.surelei.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeremiahxu.surelei.mapper.MenuMapper;
import com.jeremiahxu.surelei.po.MenuProfile;
import com.jeremiahxu.surelei.service.MenuService;

/**
 * 菜单操作服务实现类。
 * 
 * @author Jeremiah Xu
 * 
 */
@Service("menuService")
public class MenuServiceImpl implements MenuService {
	@Autowired
	private MenuMapper menuMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeremiahxu.learyperi.menu.service.MenuService#findRoot()
	 */
	@Override
	public MenuProfile findRoot() {
		return this.menuMapper.selectRoot();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeremiahxu.surelei.service.MenuService#findMenu(int)
	 */
	@Override
	public MenuProfile findMenu(int id) {
		return this.menuMapper.selectById(id);
	}

	public MenuMapper getMenuMapper() {
		return menuMapper;
	}

	public void setMenuMapper(MenuMapper menuMapper) {
		this.menuMapper = menuMapper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeremiahxu.surelei.service.MenuService#createMenu(com.jeremiahxu.surelei.po.MenuProfile)
	 */
	@Override
	@Transactional
	public void createMenu(MenuProfile menu) {
		menu.setParent(this.menuMapper.selectById(menu.getParent().getId()));
		this.menuMapper.insert(menu);
		menu.computePathAndLevel();
		this.menuMapper.update(menu);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeremiahxu.surelei.service.MenuService#deleteMenu(com.jeremiahxu.surelei.po.MenuProfile)
	 */
	@Override
	@Transactional
	public void deleteMenu(MenuProfile menu) {
		menu = this.menuMapper.selectById(menu.getId());
		if (menu.getChildren() != null) {
			for (MenuProfile m : menu.getChildren()) {
				this.deleteMenu(m);
			}
		}
		this.menuMapper.delete(menu.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeremiahxu.surelei.service.MenuService#updateMenu(com.jeremiahxu.surelei.po.MenuProfile)
	 */
	@Override
	@Transactional
	public void updateMenu(MenuProfile menu) {
		menu.setParent(this.menuMapper.selectById(menu.getParent().getId()));
		menu.computePathAndLevel();
		this.menuMapper.update(menu);
	}

}
