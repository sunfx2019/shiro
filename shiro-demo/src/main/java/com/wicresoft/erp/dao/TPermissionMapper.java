package com.wicresoft.erp.dao;

import java.util.List;

import com.wicresoft.erp.entity.TPermission;

public interface TPermissionMapper {
    /**
     *
     * @mbggenerated 2017-10-25
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2017-10-25
     */
    int insert(TPermission record);

    /**
     *
     * @mbggenerated 2017-10-25
     */
    int insertSelective(TPermission record);

    /**
     *
     * @mbggenerated 2017-10-25
     */
    TPermission selectByPrimaryKey(Long id);

	List<TPermission> findUserPermissions(String userName);

    /**
     *
     * @mbggenerated 2017-10-25
     */
    int updateByPrimaryKeySelective(TPermission record);

    /**
     *
     * @mbggenerated 2017-10-25
     */
    int updateByPrimaryKey(TPermission record);
}