package com.jeremiahxu.surelei.security;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.jeremiahxu.surelei.po.UserProfile;
import com.jeremiahxu.surelei.service.UserService;

/**
 * @author Jeremiah Xu
 *
 */
public class ShiroRealm extends AuthorizingRealm {

	@Resource(name = "userService")
	private UserService userService;

	/**
	 * 授权查询回调函数
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String name = (String) this.getAvailablePrincipal(principals);
		UserProfile user = userService.findUserByName(name);
		if (user == null) {
			return null;
		} else {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

			return info;
		}
	}

	/**
	 * 认证回调函数
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		UserProfile user = userService.findUserByName(upToken.getUsername());
		if (user != null) {
			return new SimpleAuthenticationInfo(user.getName(), user.getPassword(), this.getName());
		} else {
			return null;
		}
	}

}
