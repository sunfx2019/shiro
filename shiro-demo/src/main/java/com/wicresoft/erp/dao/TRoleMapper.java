package com.wicresoft.erp.dao;

import java.util.List;

import com.wicresoft.erp.entity.TRole;

public interface TRoleMapper {
	/**
	 *
	 * @mbggenerated 2017-10-25
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 *
	 * @mbggenerated 2017-10-25
	 */
	int insert(TRole record);

	/**
	 *
	 * @mbggenerated 2017-10-25
	 */
	int insertSelective(TRole record);

	/**
	 *
	 * @mbggenerated 2017-10-25
	 */
	TRole selectByPrimaryKey(Long id);

	/**
	 * 查询用户角色
	 * 
	 * @param userName
	 * @return
	 */
	List<TRole> findUserRoles(String userName);

	/**
	 *
	 * @mbggenerated 2017-10-25
	 */
	int updateByPrimaryKeySelective(TRole record);

	/**
	 *
	 * @mbggenerated 2017-10-25
	 */
	int updateByPrimaryKey(TRole record);
}