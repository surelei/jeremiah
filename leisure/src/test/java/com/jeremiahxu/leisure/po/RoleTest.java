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
 * OrgProfile对象持久化测试
 * 
 * @author Jeremiah Xu
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-test.xml" })
public class RoleTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Resource(name = "repo")
	private BaseRepository<ResProfile, Integer> resRepo;
	@Resource(name = "repo")
	private BaseRepository<RoleProfile, Integer> roleRepo;
	@Resource(name = "repo")
	private BaseRepository<UserProfile, Integer> userRepo;

	@Test
	@Transactional
	@Rollback
	public void roleSaveThenIdIsGreaterThanZero() {
		RoleProfile role = RoleBuilder.aRole().withCode("code1").withDescription("desc1").withName("name1").build();
		role.save();
		Assert.assertNotNull("角色保存失败", role.getId());
	}

	@Test
	@Transactional
	@Rollback
	public void roleSaveAndDeleteThenNotFound() {
		RoleProfile role1 = RoleBuilder.aRole().withCode("code1").withDescription("desc1").withName("name1").build();
		role1.save();
		int id = role1.getId();
		role1.remove();
		RoleProfile role = roleRepo.loadById(RoleProfile.class, id);
		Assert.assertNull("角色未被正确删除", role);
	}

	@Test
	@Transactional
	@Rollback
	public void roleUpdate() {
		RoleProfile role = RoleBuilder.aRole().withCode("code1").withDescription("desc1").withName("name1").build();
		role.save();
		int id = role.getId();
		RoleProfile roleUpdate = RoleBuilder.aRole().withId(id).withCode("code11").withDescription("desc11").withName("name11").build();
		roleUpdate.update();
		RoleProfile roleComp = roleRepo.loadById(RoleProfile.class, id);
		Assert.assertTrue("更新失败", roleComp.getCode().equals(roleUpdate.getCode()));
		Assert.assertTrue("更新失败", roleComp.getName().equals(roleUpdate.getName()));
		Assert.assertTrue("更新失败", roleComp.getDescription().equals(roleUpdate.getDescription()));
	}

	@Test
	@Transactional
	@Rollback
	public void threeRoleSaveThenFindThreeRole() {
		RoleProfile role1 = RoleBuilder.aRole().withCode("code1").withDescription("desc1").withName("name1").build();
		role1.save();
		RoleProfile role2 = RoleBuilder.aRole().withCode("code2").withDescription("desc2").withName("name2").build();
		role2.save();
		RoleProfile role3 = RoleBuilder.aRole().withCode("code3").withDescription("desc3").withName("name3").build();
		role3.save();
		List<RoleProfile> rs1 = roleRepo.query("select r from RoleProfile r");
		List<RoleProfile> rs2 = roleRepo.queryAll(RoleProfile.class);
		Assert.assertTrue("添加的记录条数和查询出的记录条数不一致", rs1.size() == 3);
		Assert.assertTrue("添加的记录条数和查询出的记录条数不一致", rs2.size() == 3);
	}

	@Test
	@Transactional
	@Rollback
	public void createRoleCascadeUserAndResRelation() {
		UserProfile user1 = UserBuilder.aUser().withName("name1").withPassword("pass1").withFirstName("firstname1").withLastName("lastname1").build();
		UserProfile user2 = UserBuilder.aUser().withName("name2").withPassword("pass2").withFirstName("firstname2").withLastName("lastname2").build();
		ResProfile res1 = ResBuilder.aRes().withName("name1").withUrl("url1").build();
		ResProfile res2 = ResBuilder.aRes().withName("name2").withUrl("url2").build();
		Set<UserProfile> users = new HashSet<UserProfile>();
		users.add(user1);
		users.add(user2);
		Set<ResProfile> resources = new HashSet<ResProfile>();
		resources.add(res1);
		resources.add(res2);
		RoleProfile role = RoleBuilder.aRole().withResource(resources).withUsers(users).withCode("code1").withDescription("desc1").withName("name1").build();
		user1.save();
		user2.save();
		res1.save();
		res2.save();
		role.save();
		int id = role.getId();
		RoleProfile roleComp = roleRepo.loadById(RoleProfile.class, id);
		Assert.assertTrue("角色关联的用户数量不正确", roleComp.getUsers().size() == 2);
		Assert.assertTrue("角色关联的资源数量不正确", roleComp.getResources().size() == 2);
	}

	@Test
	@Transactional
	@Rollback
	public void updateRoleCascadeUserAndResRelation() {
		UserProfile user1 = UserBuilder.aUser().withName("name1").withPassword("pass1").withFirstName("firstname1").withLastName("lastname1").build();
		UserProfile user2 = UserBuilder.aUser().withName("name2").withPassword("pass2").withFirstName("firstname2").withLastName("lastname2").build();
		ResProfile res1 = ResBuilder.aRes().withName("name1").withUrl("url1").build();
		ResProfile res2 = ResBuilder.aRes().withName("name2").withUrl("url2").build();
		RoleProfile role = RoleBuilder.aRole().withCode("code1").withDescription("desc1").withName("name1").build();
		user1.save();
		user2.save();
		res1.save();
		res2.save();
		role.save();
		int id = role.getId();
		RoleProfile roleUpdate = roleRepo.loadById(RoleProfile.class, id);
		Set<UserProfile> users = new HashSet<UserProfile>();
		users.add(user1);
		users.add(user2);
		Set<ResProfile> resources = new HashSet<ResProfile>();
		resources.add(res1);
		resources.add(res2);
		roleUpdate.setUsers(users);
		roleUpdate.setResources(resources);
		roleUpdate.update();
		RoleProfile roleComp = roleRepo.loadById(RoleProfile.class, id);
		Assert.assertTrue("角色关联的用户数量不正确", roleComp.getUsers().size() == 2);
		Assert.assertTrue("角色关联的资源数量不正确", roleComp.getResources().size() == 2);
	}

	@Test
	@Transactional
	@Rollback
	public void roleDeleteCascadeUserAndResRel() {
		UserProfile user1 = UserBuilder.aUser().withName("name1").withPassword("pass1").withFirstName("firstname1").withLastName("lastname1").build();
		UserProfile user2 = UserBuilder.aUser().withName("name2").withPassword("pass2").withFirstName("firstname2").withLastName("lastname2").build();
		ResProfile res1 = ResBuilder.aRes().withName("name1").withUrl("url1").build();
		ResProfile res2 = ResBuilder.aRes().withName("name2").withUrl("url2").build();
		Set<UserProfile> users = new HashSet<UserProfile>();
		users.add(user1);
		users.add(user2);
		Set<ResProfile> resources = new HashSet<ResProfile>();
		resources.add(res1);
		resources.add(res2);
		RoleProfile role = RoleBuilder.aRole().withUsers(users).withResource(resources).withCode("code1").withDescription("desc1").withName("name1").build();
		user1.save();
		user2.save();
		res1.save();
		res2.save();
		role.save();
		int roleId = role.getId();
		int userId1 = user1.getId();
		int userId2 = user2.getId();
		int resId1 = res1.getId();
		int resId2 = res2.getId();
		RoleProfile roleDel = roleRepo.loadById(RoleProfile.class, roleId);
		roleDel.setUsers(null);
		roleDel.setResources(null);
		roleDel.update();
		roleDel.remove();
		RoleProfile roleComp = roleRepo.loadById(RoleProfile.class, roleId);
		UserProfile userComp1 = userRepo.loadById(UserProfile.class, userId1);
		UserProfile userComp2 = userRepo.loadById(UserProfile.class, userId2);
		ResProfile resComp1 = resRepo.loadById(ResProfile.class, resId1);
		ResProfile resComp2 = resRepo.loadById(ResProfile.class, resId2);
		Assert.assertNull("角色未被正确删除", roleComp);
		Assert.assertNotNull("用户不应该被删除", userComp1);
		Assert.assertNotNull("用户不应该被删除", userComp2);
		Assert.assertNotNull("资源不应该被删除", resComp1);
		Assert.assertNotNull("资源不应该被删除", resComp2);
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

	public BaseRepository<UserProfile, Integer> getUserRepo() {
		return userRepo;
	}

	public void setUserRepo(BaseRepository<UserProfile, Integer> userRepo) {
		this.userRepo = userRepo;
	}
}
