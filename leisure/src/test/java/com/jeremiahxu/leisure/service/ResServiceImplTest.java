package com.jeremiahxu.leisure.service;

import java.util.HashSet;
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

import com.jeremiahxu.leisure.po.ResBuilder;
import com.jeremiahxu.leisure.po.ResProfile;
import com.jeremiahxu.leisure.po.RoleBuilder;
import com.jeremiahxu.leisure.po.RoleProfile;

/**
 * 资源服务实现类测试
 * 
 * @author Jeremiah Xu
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-test.xml" })
public class ResServiceImplTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Resource(name = "resService")
	private ResService resService;
	@Resource(name = "roleService")
	private RoleService roleService;

	public ResService getResService() {
		return resService;
	}

	public void setResService(ResService resService) {
		this.resService = resService;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	@Test
	@Transactional
	@Rollback(value = true)
	public void createResAndDeleteItThenNotFindIt() {
		ResProfile res = ResBuilder.aRes().withName("name1").withUrl("url1").build();
		res.save();
		Assert.assertNotNull("资源未正确添加", res.getId());
		int id = res.getId();
		ResProfile resDel = new ResProfile();
		resDel.setId(id);
		resDel.remove();
		ResProfile resComp = resService.findResource(id);
		Assert.assertNull("资源未删除", resComp);
	}

	@Test
	@Transactional
	@Rollback(value = true)
	public void updateResAndFindIt() {
		ResProfile res = ResBuilder.aRes().withName("name1").withUrl("url1").build();
		res.save();
		int id = res.getId();
		ResProfile resUpdate = ResBuilder.aRes().withId(id).withName("name11").withUrl("url11").build();
		resUpdate.update();
		ResProfile resComp = resService.findResource(id);
		Assert.assertTrue("更新失败", resComp.getName().equals(resUpdate.getName()));
		Assert.assertTrue("更新失败", resComp.getUrl().equals(resUpdate.getUrl()));
	}

	@Test
	@Transactional
	@Rollback(value = true)
	public void deleteResCascadeRoleRelation() {
		RoleProfile role = RoleBuilder.aRole().withCode("code1").withDescription("desc1").withName("name1").build();
		role.save();
		Set<RoleProfile> roles = new HashSet<RoleProfile>();
		roles.add(role);
		ResProfile res = ResBuilder.aRes().withName("name1").withUrl("url1").withRoles(roles).build();
		res.save();
		int resId = res.getId();
		int roleId = role.getId();
		ResProfile resDel = resService.findResource(resId);
		resDel.remove();
		RoleProfile roleComp = roleService.findRole(roleId);
		Assert.assertNotNull("角色不应该被删除", roleComp);
	}
}
