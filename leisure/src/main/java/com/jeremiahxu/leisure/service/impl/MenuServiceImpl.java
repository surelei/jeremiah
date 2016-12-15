package com.jeremiahxu.leisure.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jeremiahxu.leisure.po.MenuProfile;
import com.jeremiahxu.leisure.repository.BaseRepository;
import com.jeremiahxu.leisure.service.MenuService;

/**
 * 菜单操作服务实现类。
 * 
 * @author Jeremiah Xu
 * 
 */
@Service("menuService")
public class MenuServiceImpl implements MenuService {

	@Resource(name = "repo")
	private BaseRepository<MenuProfile, Integer> menuRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeremiahxu.learyperi.menu.service.MenuService#findRoot()
	 */
	@Override
	public MenuProfile findRoot() {
		String jpql = "select m from MenuProfile m where m.code=:code and m.level=:level";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("code", "root_menu");
		params.put("level", 0);
		return this.getMenuRepo().load(jpql, params);
	}

	public BaseRepository<MenuProfile, Integer> getMenuRepo() {
		return menuRepo;
	}

	public void setMenuRepo(BaseRepository<MenuProfile, Integer> menuRepo) {
		this.menuRepo = menuRepo;
	}

	public MenuProfile findMenu(int id) {
		return this.getMenuRepo().loadById(MenuProfile.class, id);
	}

}
