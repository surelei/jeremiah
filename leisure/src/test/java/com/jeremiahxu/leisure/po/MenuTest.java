package com.jeremiahxu.leisure.po;

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

import com.jeremiahxu.leisure.repository.BaseRepository;

/**
 * Menu对象持久化测试
 * 
 * @author Jeremiah Xu
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-test.xml" })
public class MenuTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Resource(name = "repo")
	private BaseRepository<MenuProfile, Integer> menuRepo;

	@Test
	@Transactional
	@Rollback
	public void menuSaveThenIdIsGreaterThanZero() {
		MenuProfile menu = MenuBuilder.aMenu().withCode("code1").withName("菜单1").withType(1).withLevel(1).withOrder(1).withImagePath("imagepath1").withUrl("url1").build();
		menu.save();
		Assert.assertNotNull("菜单未保存", menu.getId());
	}

	@Test
	@Transactional
	@Rollback
	public void threeMenuSaveThenParentIdIsCorrect() {
		MenuProfile menu11 = MenuBuilder.aMenu().withCode("code11").withName("菜单11").withType(1).withLevel(1).withOrder(1).withImagePath("imagepath11").withUrl("url11").build();
		MenuProfile menu12 = MenuBuilder.aMenu().withCode("code12").withName("菜单12").withType(1).withLevel(1).withOrder(1).withImagePath("imagepath12").withUrl("url12").build();
		List<MenuProfile> children = new ArrayList<MenuProfile>();
		children.add(menu11);
		children.add(menu12);
		MenuProfile menu1 = MenuBuilder.aMenu().withCode("code1").withName("菜单1").withType(1).withLevel(1).withOrder(1).withImagePath("imagepath1").withUrl("url1").withChildren(children).build();
		menu1.save();
		int id = menu1.getId();
		int id11 = menu11.getId();
		int id12 = menu12.getId();
		MenuProfile menuComp = menuRepo.loadById(MenuProfile.class, id);
		MenuProfile menuComp11 = menuRepo.loadById(MenuProfile.class, id11);
		MenuProfile menuComp12 = menuRepo.loadById(MenuProfile.class, id12);
		Assert.assertTrue("菜单保存后子菜单未保存", menuComp.getChildren().size() == 2);
		Assert.assertTrue("子菜单保存后的父节点不正确", menuComp11.getParent().getId() == menuComp.getId());
		Assert.assertTrue("子菜单保存后的父节点不正确", menuComp12.getParent().getId() == menuComp.getId());
	}

	@Test
	@Transactional
	@Rollback
	public void menuSaveAndDeleteThenNotFound() {
		MenuProfile menu1 = MenuBuilder.aMenu().withCode("code1").withName("菜单1").withImagePath("imagepath1").withUrl("url1").build();
		menu1.save();
		int id = menu1.getId();
		MenuProfile menu2 = menuRepo.loadById(MenuProfile.class, id);
		menu2.remove();
		MenuProfile menu = menuRepo.loadById(MenuProfile.class, id);
		Assert.assertNull("菜单项未被删除", menu);
	}

	@Test
	@Transactional
	@Rollback
	public void menuUpdate() {
		MenuProfile menu = MenuBuilder.aMenu().withCode("code1").withName("菜单1").withImagePath("imagepath1").withUrl("url1").withLevel(1).withIdPath("/1/").withNamePath("/菜单1/").build();
		menu.save();
		int id = menu.getId();
		MenuProfile menuUpdate = MenuBuilder.aMenu().withCode("code2").withName("菜单2").withImagePath("imagepath2").withUrl("url2").withLevel(2).withIdPath("/2/").withNamePath("/菜单2/").withId(id).build();
		menuUpdate.update();
		MenuProfile menuComp = menuRepo.loadById(MenuProfile.class, id);
		Assert.assertTrue("更新失败", menuComp.getCode().equals(menuUpdate.getCode()));
		Assert.assertTrue("更新失败", menuComp.getName().equals(menuUpdate.getName()));
		Assert.assertTrue("更新失败", menuComp.getImagePath().equals(menuUpdate.getImagePath()));
		Assert.assertTrue("更新失败", menuComp.getUrl().equals(menuUpdate.getUrl()));
		Assert.assertTrue("更新失败", menuComp.getLevel() == menuUpdate.getLevel());
		Assert.assertTrue("更新失败", menuComp.getIdPath().equals(menuUpdate.getIdPath()));
		Assert.assertTrue("更新失败", menuComp.getNamePath().equals(menuUpdate.getNamePath()));
	}

	@Test
	@Transactional
	@Rollback
	public void threeMenuSaveThenFindThreeMenu() {
		MenuProfile menu1 = MenuBuilder.aMenu().withCode("code1").withName("菜单1").withImagePath("imagepath1").withUrl("url1").withType(1).withOrder(1).withLevel(1).withIdPath("/1/").withNamePath("/菜单1/").build();
		menu1.save();
		MenuProfile menu2 = MenuBuilder.aMenu().withParent(menu1).withCode("code2").withName("菜单2").withImagePath("imagepath2").withUrl("url2").withType(1).withOrder(10).withLevel(1).withIdPath("/2/").withNamePath("/菜单2/").build();
		menu2.save();
		MenuProfile menu3 = MenuBuilder.aMenu().withParent(menu1).withCode("code3").withName("菜单3").withImagePath("imagepath3").withUrl("url3").withType(1).withOrder(20).withLevel(1).withIdPath("/3/").withNamePath("/菜单3/").build();
		menu3.save();
		List<MenuProfile> rs1 = menuRepo.query("select m from MenuProfile m");
		List<MenuProfile> rs2 = menuRepo.queryAll(MenuProfile.class);
		Assert.assertTrue("添加的记录条数和查询出的记录条数不一致", rs1.size() == 3);
		Assert.assertTrue("添加的记录条数和查询出的记录条数不一致", rs2.size() == 3);
	}

	@Test
	@Transactional
	@Rollback
	public void menuSaveAndParentNotSave() {
		MenuProfile menu1 = MenuBuilder.aMenu().withCode("code1").withName("菜单1").withType(1).withLevel(1).withOrder(1).withImagePath("imagepath1").withUrl("url1").build();
		MenuProfile menu11 = MenuBuilder.aMenu().withCode("code11").withName("菜单11").withType(1).withLevel(1).withOrder(1).withImagePath("imagepath11").withUrl("url11").withParent(menu1).build();
		menu11.save();
		int id = menu11.getId();
		menu11.setParent(null);
		MenuProfile menuComp = menuRepo.loadById(MenuProfile.class, id);
		Assert.assertNull("保存菜单时父菜单不应该被保存", menuComp.getParent());
	}

	public BaseRepository<MenuProfile, Integer> getMenuRepo() {
		return menuRepo;
	}

	public void setMenuRepo(BaseRepository<MenuProfile, Integer> menuRepo) {
		this.menuRepo = menuRepo;
	}

}
