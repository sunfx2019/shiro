package com.wicresoft.erp.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wicresoft.erp.dao.TPermissionMapper;
import com.wicresoft.erp.entity.TPermission;
import com.wicresoft.erp.service.IPermissService;

@Service("permissServiceImpl")
public class IPermissServiceImpl implements IPermissService {

	@Autowired
	private TPermissionMapper tPermissionMapper;

	@Override
	public List<TPermission> findAll() {
		return tPermissionMapper.getAll();
	}

	@Override
	public List<Map<Object, Object>> findPermissionRole() {
		return tPermissionMapper.findPermissionRole();
	}

}
