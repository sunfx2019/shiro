package com.wicresoft.erp.service;

import java.util.List;

import com.wicresoft.erp.entity.TRole;
import com.wicresoft.erp.entity.TUser;

/**
 * 用户Service
 * 
 * @author Administrator
 *
 */
public interface IUserService {

	public TUser findUserByUserName(String userName);

	public List<TRole> findUserRoles(String userName);

	public List<String> findUserPermissions(String userName);

}
