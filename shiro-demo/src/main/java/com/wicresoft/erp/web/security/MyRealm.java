package com.wicresoft.erp.web.security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.alibaba.fastjson.JSONArray;
import com.wicresoft.erp.entity.TPermission;
import com.wicresoft.erp.entity.TRole;
import com.wicresoft.erp.entity.TUser;
import com.wicresoft.erp.service.IUserService;
import com.wicresoft.erp.web.security.encryption.ThreeDesUtil;

/**
 * Realm 验证
 * 
 * @author Administrator
 *
 */
public class MyRealm extends AuthorizingRealm {

	// 用户Service
	private IUserService userService;

	// 3Des加密
	private ThreeDesUtil threeDes = ThreeDesUtil.getInstance();

	private Log log4j = LogFactory.getLog(getClass());

	private final static String key = "073188992648forenetf8ssc";

	private final static String iv = "88888888";

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		log4j.info("----------40 doGetAuthorizationInfo方法被调用----------");
		String userName = (String) getAvailablePrincipal(principals);
		List<TRole> rolsList = userService.findUserRoles(userName);
		List<TPermission> permissionList = userService.findUserPermissions(userName);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// 权限
		Set<String> s = new HashSet<String>();
		for (TRole role : rolsList) {
			s.add(role.getName());
		}
		// 角色
		Set<String> r = new HashSet<String>();
		for (TPermission permission : permissionList) {
			r.add(permission.getUrl());
		}
		info.setRoles(r);
		info.setStringPermissions(s);
		log4j.info(JSONArray.toJSON(info));
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		log4j.info("----------68 doGetAuthenticationInfo方法被调用----------");
		String userName = (String) token.getPrincipal(); // 用户名
		String password = new String((char[]) token.getCredentials()); // 密码
		log4j.info("userName:" + userName);
		log4j.info("password:" + password);
		TUser user = userService.findUserByUserName(userName);
		String encryptPassword = encryptPassword(password);
		if (user == null) {
			throw new UnknownAccountException("用户不存在！");
		}
		if (!(user.getPswd()).equals(encryptPassword)) {
			throw new IncorrectCredentialsException("密码错误！");
		}
		// 身份验证通过,返回一个身份信息
		AuthenticationInfo aInfo = new SimpleAuthenticationInfo(userName, password, getName());
		return aInfo;
	}

	/**
	 * 密码加密
	 * 
	 * @param password
	 *            原始密码
	 * @return
	 */
	public String encryptPassword(String password) {
		try {
			return threeDes.encrypt(key, iv, password);
		} catch (Exception e) {
			log4j.error(e.getMessage(), e);
			return null;
		}
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

}
