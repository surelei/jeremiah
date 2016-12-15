package com.jeremiahxu.surelei.action;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeremiahxu.surelei.po.UserProfile;
import com.jeremiahxu.surelei.service.MenuService;
import com.jeremiahxu.surelei.service.UserService;

/**
 * 首页跳转
 * 
 * @author Jeremiah Xu
 * 
 */
@Controller
@RequestMapping(value = "/index")
public class IndexAction {
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	@Resource(name = "menuService")
	private MenuService menuService;
	@Resource(name = "userService")
	private UserService userService;
	private UserProfile user;
	private String type;// 进入登录页面的原因类型
	private String tip;// 在登录页面显示的提示信息

	/**
	 * 跳转去首页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/home")
	public String home() throws Exception {
		log.info("to main page.");
		return "main";
	}

	/**
	 * 跳转去登录页面。
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toLogin")
	public String toLogin() throws Exception {
		// 登录失败后进入登录页面的情况
		if ("login_failure".equals(type)) {
			this.setTip("用户名或密码错误！");
		} else if ("session_timeout".equals(type)) {
			this.setTip("操作超时，请重新登录！");
		} else {
			this.setTip("请输入用户名和密码！");
		}
		log.info("toLogin.");
		return "";
	}

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserProfile getUser() {
		return user;
	}

	public void setUser(UserProfile user) {
		this.user = user;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

}
