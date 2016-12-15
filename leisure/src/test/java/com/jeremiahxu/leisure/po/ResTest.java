package com.jeremiahxu.leisure.po;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
 * ResProfile对象持久化测试
 * 
 * @author Jeremiah Xu
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-test.xml" })
public class ResTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Resource(name = "repo")
	private BaseRepository<ResProfile, Integer> resRepo;
	@Resource(name = "repo")
	private BaseRepository<RoleProfile, Integer> roleRepo;

	@Test
	@Transactional
	@Rollback
	public void resSaveThenIdIsGreaterThanZero() {
		ResProfile res = ResBuilder.aRes().withName("name1").withUrl("url1").build();
		res.save();
		int id = res.getId();
		Assert.assertTrue("资源保存失败", id > 0);
	}

	@Test
	@Transactional
	@Rollback
	public void resSaveAndDeleteThenNotFound() {
		ResProfile res1 = ResBuilder.aRes().withName("name1").withUrl("url1").build();
		res1.save();
		int id = res1.getId();
		ResProfile resDel = resRepo.loadById(ResProfile.class, id);
		resDel.remove();
		ResProfile res = resRepo.loadById(ResProfile.class, id);
		Assert.assertNull("资源未被正确删除", res);
	}

	@Test
	@Transactional
	@Rollback
	public void resUpdate() {
		ResProfile res = ResBuilder.aRes().withName("name1").withUrl("url1").build();
		res.save();
		int id = res.getId();
		ResProfile resUpdate = ResBuilder.aRes().withId(id).withName("name2").withUrl("url2").build();
		resUpdate.update();
		ResProfile resComp = resRepo.loadById(ResProfile.class, id);
		Assert.assertTrue("更新失败", resComp.getName().equals(resUpdate.getName()));
		Assert.assertTrue("更新失败", resComp.getUrl().equals(resUpdate.getUrl()));
	}

	@Test
	@Transactional
	@Rollback
	public void threeResSaveThenFindThreeRes() {
		ResProfile res1 = ResBuilder.aRes().withName("name1").withUrl("url1").build();
		res1.save();
		ResProfile res2 = ResBuilder.aRes().withName("name2").withUrl("url2").build();
		res2.save();
		ResProfile res3 = ResBuilder.aRes().withName("name3").withUrl("url3").build();
		res3.save();
		List<ResProfile> rs1 = resRepo.query("select r from ResProfile r");
		List<ResProfile> rs2 = resRepo.queryAll(ResProfile.class);
		Assert.assertTrue("添加的记录条数和查询出的记录条数不一致", rs1.size() == 3);
		Assert.assertTrue("添加的记录条数和查询出的记录条数不一致", rs2.size() == 3);
	}

	@Test
	@Transactional
	@Rollback
	public void resCreateCascadeRoleRel() {
		RoleProfile role1 = RoleBuilder.aRole().withCode("role_code1").withName("role_name1").withDescription("role_des1").build();
		RoleProfile role2 = RoleBuilder.aRole().withCode("role_code2").withName("role_name2").withDescription("role_des2").build();
		Set<RoleProfile> roles = new HashSet<RoleProfile>();
		roles.add(role1);
		roles.add(role2);
		ResProfile res = ResBuilder.aRes().withName("res_name").withUrl("res_url").withRoles(roles).build();
		role1.save();
		role2.save();
		res.save();
		int id = res.getId();
		ResProfile resComp = resRepo.loadById(ResProfile.class, id);
		Assert.assertTrue("角色数量不正确", resComp.getRoles().size() == 2);
	}

	@Test
	@Transactional
	@Rollback
	public void resUpdateCascadeRoleRel() {
		RoleProfile role1 = RoleBuilder.aRole().withCode("role_code1").withName("role_name1").withDescription("role_des1").build();
		RoleProfile role2 = RoleBuilder.aRole().withCode("role_code2").withName("role_name2").withDescription("role_des2").build();
		Set<RoleProfile> roles = new HashSet<RoleProfile>();
		roles.add(role1);
		roles.add(role2);
		ResProfile res = ResBuilder.aRes().withName("res_name").withUrl("res_url").build();
		role1.save();
		role2.save();
		res.save();
		int id = res.getId();
		ResProfile resComp1 = resRepo.loadById(ResProfile.class, id);
		Assert.assertTrue("角色关系不应该被保存", resComp1.getRoles().size() == 0);
		resComp1.setRoles(roles);
		resComp1.update();
		ResProfile resComp2 = resRepo.loadById(ResProfile.class, id);
		Assert.assertTrue("角色数量不正确", resComp2.getRoles().size() == 2);
	}

	@Test
	@Transactional
	@Rollback
	public void resDeleteCascadeRole() {
		RoleProfile role1 = RoleBuilder.aRole().withCode("role_code1").withName("role_name1").withDescription("role_des1").build();
		RoleProfile role2 = RoleBuilder.aRole().withCode("role_code2").withName("role_name2").withDescription("role_des2").build();
		Set<RoleProfile> roles = new HashSet<RoleProfile>();
		roles.add(role1);
		roles.add(role2);
		ResProfile res = ResBuilder.aRes().withName("res_name").withUrl("res_url").withRoles(roles).build();
		role1.save();
		role2.save();
		res.save();
		int resId = res.getId();
		int roleId1 = role1.getId();
		int roleId2 = role2.getId();
		ResProfile resDel = resRepo.loadById(ResProfile.class, resId);
		resDel.setRoles(null);
		resDel.update();
		resDel.remove();
		ResProfile resComp = resRepo.loadById(ResProfile.class, resId);
		RoleProfile roleComp1 = roleRepo.loadById(RoleProfile.class, roleId1);
		RoleProfile roleComp2 = roleRepo.loadById(RoleProfile.class, roleId2);
		Assert.assertNull("资源未删除", resComp);
		Assert.assertNotNull("角色不应该被删除", roleComp1);
		Assert.assertNotNull("角色不应该被删除", roleComp2);
	}

	public BaseRepository<ResProfile, Integer> getResRepo() {
		return resRepo;
	}

	public void setResRepo(BaseRepository<ResProfile, Integer> resRepo) {
		this.resRepo = resRepo;
	}

	public BaseRepository<RoleProfile, Integer> getRoleRepo() {
		return roleRepo;
	}

	public void setRoleRepo(BaseRepository<RoleProfile, Integer> roleRepo) {
		this.roleRepo = roleRepo;
	}
}
