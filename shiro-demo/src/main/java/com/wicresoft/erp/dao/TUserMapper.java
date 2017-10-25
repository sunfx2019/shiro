package com.wicresoft.erp.dao;

import com.wicresoft.erp.entity.TUser;

public interface TUserMapper {
	/**
	 *
	 * @mbggenerated 2017-10-25
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 *
	 * @mbggenerated 2017-10-25
	 */
	int insert(TUser record);

	/**
	 *
	 * @mbggenerated 2017-10-25
	 */
	int insertSelective(TUser record);

	/**
	 *
	 * @mbggenerated 2017-10-25
	 */
	TUser selectByPrimaryKey(Long id);

	TUser finByUserName(String userName);

	/**
	 *
	 * @mbggenerated 2017-10-25
	 */
	int updateByPrimaryKeySelective(TUser record);

	/**
	 *
	 * @mbggenerated 2017-10-25
	 */
	int updateByPrimaryKey(TUser record);
}