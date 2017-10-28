package com.wicresoft.erp.service;

import java.util.List;

import com.wicresoft.erp.entity.TUser;

/**
 * 用户Service
 * 
 * @author Administrator
 *
 */
public interface IUserService {


	public List<TUser> fiandAll();
	
	public TUser findUserByUserName(String userName);

	public List<String> findUserRoles(String userName);

	public List<String> findUserPermissions(String userName);

}
