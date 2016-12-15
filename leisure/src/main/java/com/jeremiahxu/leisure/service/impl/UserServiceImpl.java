package com.jeremiahxu.leisure.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jeremiahxu.leisure.exception.UserException;
import com.jeremiahxu.leisure.po.UserProfile;
import com.jeremiahxu.leisure.repository.BaseRepository;
import com.jeremiahxu.leisure.service.UserService;

/**
 * 用户管理服务实现类。
 * 
 * @author Jeremiah Xu
 * 
 */
@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource(name = "repo")
	private BaseRepository<UserProfile, Integer> userRepo;

	@Override
	public UserProfile findUserByName(String name) {
		String jpql = "select u from UserProfile u where u.name=:name";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);
		List<UserProfile> rs = this.getUserRepo().query(jpql, params);
		if (rs.size() < 1) {// 未取得用户信息
			throw new UserException("user[" + name + "] is not exists.");
		} else if (rs.size() == 1) {// 正确取得用户信息
			return rs.get(0);
		} else {// 取得的用户信息有多条
			throw new UserException("user[" + name + "] is not only one.");
		}
	}

	@Override
	public UserProfile findUserByNameAndPassword(String name, String password) {
		String jpql = "select u from UserProfile u where u.name=:name and u.password=:password";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);
		params.put("password", password);
		List<UserProfile> rs = userRepo.query(jpql, params);
		if (rs.size() < 1) {// 用户登录失败
			return null;
		} else if (rs.size() == 1) {// 正确取得用户信息，用户登录成功。
			return rs.get(0);
		} else {// 取得用户信息超过一条
			throw new UserException("user[" + name + "] is not only one.");
		}
	}

	@Override
	public List<UserProfile> findAllUser() {
		return this.userRepo.queryAll(UserProfile.class);
	}

	@Override
	public UserProfile findUser(int id) {
		return this.userRepo.loadById(UserProfile.class, id);
	}

	public BaseRepository<UserProfile, Integer> getUserRepo() {
		return userRepo;
	}

	public void setUserRepo(BaseRepository<UserProfile, Integer> userRepo) {
		this.userRepo = userRepo;
	}
}
