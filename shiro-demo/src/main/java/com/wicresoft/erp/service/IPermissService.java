package com.wicresoft.erp.service;

import java.util.List;
import java.util.Map;

import com.wicresoft.erp.entity.TPermission;

public interface IPermissService {

	public List<TPermission> findAll();

	/**
	 * 查询资源对应的权限
	 * 
	 * @return
	 */
	public List<Map<Object, Object>> findPermissionRole();

}
