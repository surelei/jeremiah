package com.jeremiahxu.leisure.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jeremiahxu.leisure.po.MenuBuilder;
import com.jeremiahxu.leisure.po.MenuProfile;

/**
 * 菜单服务实现类测试
 * 
 * @author Jeremiah Xu
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-test.xml" })
public class MenuServiceImplTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Resource(name = "menuService")
	private MenuService menuService;

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	@Test
	@Transactional
	@Rollback(value = true)
	public void updateMenuAndFindIt() {
		MenuProfile menu = MenuBuilder.aMenu().withCode("code1").withName("菜单1").withType(1).withOrder(1).withLevel(1).withImagePath("imagepath1").withUrl("url1").build();
		menu.save();
		int id = menu.getId();
		MenuProfile menuUpdate = MenuBuilder.aMenu().withId(id).withCode("code2").withName("菜单2").withType(2).withOrder(2).withLevel(2).withImagePath("imagepath2").withUrl("url2").build();
		menuUpdate.update();
		MenuProfile menuComp = menuService.findMenu(id);
		Assert.assertTrue("更新失败", menuComp.getCode().equals(menuUpdate.getCode()));
		Assert.assertTrue("更新失败", menuComp.getName().equals(menuUpdate.getName()));
		Assert.assertTrue("更新失败", menuComp.getImagePath().equals(menuUpdate.getImagePath()));
		Assert.assertTrue("更新失败", menuComp.getUrl().equals(menuUpdate.getUrl()));
		Assert.assertTrue("更新失败", menuComp.getType() == menuUpdate.getType());
		Assert.assertTrue("更新失败", menuComp.getOrder() == menuUpdate.getOrder());
		Assert.assertTrue("更新失败", menuComp.getLevel() == menuUpdate.getLevel());
	}

	@Test
	@Transactional
	@Rollback(value = true)
	public void checkOrder() {
		int order1 = 20;
		int order2 = 10;
		int order3 = 30;
		MenuProfile menu = MenuBuilder.aMenu().withCode("code").withName("菜单").withType(1).withOrder(1).withLevel(0).withImagePath("imagepath1").withUrl("url1").build();
		MenuProfile menu1 = MenuBuilder.aMenu().withParent(menu).withCode("code1").withName("菜单1").withType(1).withOrder(order1).withImagePath("imagepath1").withUrl("url1").build();
		MenuProfile menu2 = MenuBuilder.aMenu().withParent(menu).withCode("code2").withName("菜单2").withType(1).withOrder(order2).withImagePath("imagepath2").withUrl("url2").build();
		MenuProfile menu3 = MenuBuilder.aMenu().withParent(menu).withCode("code3").withName("菜单3").withType(1).withOrder(order3).withImagePath("imagepath3").withUrl("url3").build();
		List<MenuProfile> children = new ArrayList<MenuProfile>();
		children.add(menu1);
		children.add(menu2);
		children.add(menu3);
		menu.setChildren(children);
		menu.save();
		int id = menu.getId();
		menu = null;
		MenuProfile parent = menuService.findMenu(id);
		List<MenuProfile> menuList = parent.getChildren();
		Assert.assertTrue("子菜单顺序错误", (menuList.get(0).getOrder() == order2) && (menuList.get(1).getOrder() == order1) && (menuList.get(2).getOrder() == order3));
	}

	@Test
	@Transactional
	@Rollback(value = true)
	public void createRootMenuAndFingRootMenu() {
		MenuProfile menu = MenuBuilder.aMenu().withCode("root_menu").withName("菜单1").withType(1).withOrder(1).withImagePath("imagepath1").withUrl("url1").build();
		menu.save();
		int id = menu.getId();
		MenuProfile menuComp = menuService.findRoot();
		Assert.assertTrue("创建菜单失败", menu.equals(menuComp));
		Assert.assertTrue("idpath不正确", menuComp.getIdPath().equals("/" + id + "/"));
		Assert.assertTrue("level不正确", menuComp.getLevel() == 0);
		Assert.assertTrue("namepath不正确", menuComp.getNamePath().equals("/菜单1/"));
	}
}
