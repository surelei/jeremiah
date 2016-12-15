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

import com.jeremiahxu.leisure.po.OrgBuilder;
import com.jeremiahxu.leisure.po.OrgProfile;

/**
 * 组织机构服务实现类测试
 * 
 * @author Jeremiah Xu
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-test.xml" })
public class OrgServiceImplTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Resource(name = "orgService")
	private OrgService orgService;

	public OrgService getOrgService() {
		return orgService;
	}

	public void setOrgService(OrgService orgService) {
		this.orgService = orgService;
	}

	@Test
	@Transactional
	@Rollback(value = true)
	public void createOrgAndDeleteItThenNotFindIt() {
		OrgProfile org = OrgBuilder.aOrg().withCode("code1").withDescription("desc1").withName("name1").withOrder(1).build();
		org.save();
		int id = org.getId();
		OrgProfile orgDel = new OrgProfile();
		orgDel.setId(id);
		orgDel.remove();
		OrgProfile orgComp = orgService.findOrg(id);
		Assert.assertNull("组织机构未删除", orgComp);
	}

	@Test
	@Transactional
	@Rollback(value = true)
	public void updateOrgAndFindIt() {
		OrgProfile org = OrgBuilder.aOrg().withCode("code1").withDescription("desc1").withName("name1").withOrder(1).build();
		org.save();
		int id = org.getId();
		OrgProfile orgUpdate = OrgBuilder.aOrg().withId(id).withCode("code2").withDescription("desc2").withName("name2").withOrder(2).build();
		orgUpdate.update();
		OrgProfile orgComp = orgService.findOrg(id);
		Assert.assertTrue("更新失败", orgComp.getCode().equals(orgUpdate.getCode()));
		Assert.assertTrue("更新失败", orgComp.getName().equals(orgUpdate.getName()));
		Assert.assertTrue("更新失败", orgComp.getOrder() == orgUpdate.getOrder());
		Assert.assertTrue("更新失败", orgComp.getDescription().equals(orgUpdate.getDescription()));
	}

	@Test
	@Transactional
	@Rollback(value = true)
	public void checkOrder() {
		int order1 = 20;
		int order2 = 10;
		int order3 = 30;
		OrgProfile org = OrgBuilder.aOrg().withCode("code").withDescription("desc").withName("name").withOrder(1).withLevel(0).build();
		OrgProfile org1 = OrgBuilder.aOrg().withParent(org).withCode("code1").withDescription("desc1").withName("name1").withOrder(order1).build();
		OrgProfile org2 = OrgBuilder.aOrg().withParent(org).withCode("code2").withDescription("desc2").withName("name2").withOrder(order2).build();
		OrgProfile org3 = OrgBuilder.aOrg().withParent(org).withCode("code3").withDescription("desc3").withName("name3").withOrder(order3).build();
		List<OrgProfile> children = new ArrayList<OrgProfile>();
		children.add(org1);
		children.add(org2);
		children.add(org3);
		org.setChildren(children);
		org.save();
		int id = org.getId();
		org = null;
		OrgProfile parent = orgService.findOrg(id);
		List<OrgProfile> orgList = parent.getChildren();
		Assert.assertTrue("组织结构顺序错误", (orgList.get(0).getOrder() == order2) && (orgList.get(1).getOrder() == order1) && (orgList.get(2).getOrder() == order3));
	}

	@Test
	@Transactional
	@Rollback(value = true)
	public void createRootOrgAndFindRootOrg() {
		OrgProfile org = OrgBuilder.aOrg().withCode("root_org").withLevel(0).withDescription("desc1").withName("name1").withOrder(1).build();
		org.save();
		int id = org.getId();
		OrgProfile orgComp = orgService.findRoot();
		Assert.assertEquals("创建组织机构失败", org, orgComp);
		Assert.assertEquals("idpath不正确", orgComp.getIdPath(), "/" + id + "/");
		Assert.assertEquals("level不正确", orgComp.getLevel(), 0);
		Assert.assertEquals("namepath不正确", orgComp.getNamePath(), "/name1/");
	}

}
