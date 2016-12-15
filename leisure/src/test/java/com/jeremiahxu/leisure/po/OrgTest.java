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
 * OrgProfile对象持久化测试
 * 
 * @author Jeremiah Xu
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-test.xml" })
public class OrgTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Resource(name = "repo")
	private BaseRepository<OrgProfile, Integer> orgRepo;

	@Test
	@Transactional
	@Rollback
	public void orgSaveThenIdIsGreaterThanZero() {
		OrgProfile org = OrgBuilder.aOrg().withCode("code1").withDescription("desc1").withLevel(1).withName("name1").withOrder(11).build();
		org.save();
		int id = org.getId();
		Assert.assertTrue("组织机构保存失败", id > 0);
	}

	@Test
	@Transactional
	@Rollback
	public void threeOrgSaveThenParentIdIsCorrect() {
		OrgProfile org11 = OrgBuilder.aOrg().withCode("code11").withDescription("desc11").withLevel(1).withName("name11").withOrder(1).build();
		OrgProfile org12 = OrgBuilder.aOrg().withCode("code12").withDescription("desc12").withLevel(1).withName("name12").withOrder(1).build();
		List<OrgProfile> children = new ArrayList<OrgProfile>();
		children.add(org11);
		children.add(org12);
		OrgProfile org1 = OrgBuilder.aOrg().withCode("code1").withDescription("desc1").withLevel(1).withName("name1").withOrder(1).withChildren(children).build();
		org1.save();
		int id = org1.getId();
		int id11 = org11.getId();
		int id12 = org12.getId();
		OrgProfile orgComp = orgRepo.loadById(OrgProfile.class, id);
		OrgProfile orgComp11 = orgRepo.loadById(OrgProfile.class, id11);
		OrgProfile orgComp12 = orgRepo.loadById(OrgProfile.class, id12);
		Assert.assertTrue("组织结构子节点数量不正确", orgComp.getChildren().size() == 2);
		Assert.assertTrue("组织机构保存后父节点不正确", orgComp11.getParent().getId() == orgComp.getId());
		Assert.assertTrue("组织机构保存后的父节点不正确", orgComp12.getParent().getId() == orgComp.getId());
	}

	@Test
	@Transactional
	@Rollback
	public void orgSaveAndDeleteThenNotFound() {
		OrgProfile org1 = OrgBuilder.aOrg().withCode("code1").withDescription("desc1").withLevel(1).withName("name1").withOrder(1).build();
		org1.save();
		int id = org1.getId();
		OrgProfile orgDel = orgRepo.loadById(OrgProfile.class, id);
		orgDel.remove();
		OrgProfile org = orgRepo.loadById(OrgProfile.class, id);
		Assert.assertNull("组织结构未被正确删除", org);
	}

	@Test
	@Transactional
	@Rollback
	public void orgUpdate() {
		OrgProfile org = OrgBuilder.aOrg().withCode("code1").withDescription("desc1").withLevel(1).withName("name1").withOrder(11).build();
		org.save();
		int id = org.getId();
		OrgProfile orgUpdate = OrgBuilder.aOrg().withId(id).withCode("code11").withDescription("desc11").withLevel(2).withName("name11").withOrder(2).build();
		orgUpdate.update();
		OrgProfile orgComp = orgRepo.loadById(OrgProfile.class, id);
		Assert.assertTrue("更新失败", orgComp.getCode().equals(orgUpdate.getCode()));
		Assert.assertTrue("更新失败", orgComp.getName().equals(orgUpdate.getName()));
		Assert.assertTrue("更新失败", orgComp.getDescription().equals(orgUpdate.getDescription()));
		Assert.assertTrue("更新失败", orgComp.getOrder() == orgUpdate.getOrder());
		Assert.assertTrue("更新失败", orgComp.getLevel() == orgUpdate.getLevel());
		Assert.assertTrue("更新失败", orgComp.getIdPath().equals(orgUpdate.getIdPath()));
		Assert.assertTrue("更新失败", orgComp.getNamePath().equals(orgUpdate.getNamePath()));
	}

	@Test
	@Transactional
	@Rollback
	public void threeOrgSaveThenFindThreeOrg() {
		OrgProfile org1 = OrgBuilder.aOrg().withCode("code1").withDescription("desc1").withLevel(1).withName("name1").withOrder(1).build();
		org1.save();
		OrgProfile org2 = OrgBuilder.aOrg().withCode("code2").withDescription("desc2").withLevel(2).withName("name2").withOrder(1).build();
		org2.save();
		OrgProfile org3 = OrgBuilder.aOrg().withCode("code3").withDescription("desc3").withLevel(1).withName("name3").withOrder(1).build();
		org3.save();
		List<OrgProfile> rs1 = orgRepo.query("select o from OrgProfile o");
		List<OrgProfile> rs2 = orgRepo.queryAll(OrgProfile.class);
		Assert.assertTrue("添加的记录条数和查询出的记录条数不一致", rs1.size() == 3);
		Assert.assertTrue("添加的记录条数和查询出的记录条数不一致", rs2.size() == 3);
	}

	@Test
	@Transactional
	@Rollback
	public void orgSaveAndParentNotSave() {
		OrgProfile org1 = OrgBuilder.aOrg().withCode("code1").withDescription("desc1").withLevel(1).withName("name1").withOrder(1).build();
		OrgProfile org11 = OrgBuilder.aOrg().withCode("code11").withDescription("desc11").withLevel(1).withName("name11").withOrder(1).withParent(org1).build();
		org11.save();
		int id = org11.getId();
		org11.setParent(null);
		OrgProfile orgComp = orgRepo.loadById(OrgProfile.class, id);
		Assert.assertNull("组织结构保存后父组织结构不应该被保存", orgComp.getParent());
	}

	public BaseRepository<OrgProfile, Integer> getOrgRepo() {
		return orgRepo;
	}

	public void setOrgRepo(BaseRepository<OrgProfile, Integer> orgRepo) {
		this.orgRepo = orgRepo;
	}

}
