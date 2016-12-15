package com.jeremiahxu.leisure.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jeremiahxu.leisure.po.ResProfile;
import com.jeremiahxu.leisure.repository.BaseRepository;
import com.jeremiahxu.leisure.service.ResService;

/**
 * 资源服务实现类。
 * 
 * @author Jeremiah Xu
 * 
 */
@Service("resService")
public class ResServiceImpl implements ResService {
	@Resource(name = "repo")
	private BaseRepository<ResProfile, Integer> resRepo;

	@Override
	public List<ResProfile> findAllResource() {
		return this.resRepo.queryAll(ResProfile.class);
	}

	@Override
	public ResProfile findResource(int id) {
		return this.resRepo.loadById(ResProfile.class, id);
	}

	public BaseRepository<ResProfile, Integer> getRepo() {
		return resRepo;
	}

	public void setRepo(BaseRepository<ResProfile, Integer> resRepo) {
		this.resRepo = resRepo;
	}

}
