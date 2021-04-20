package com.cjl.basic.zone.project.role.mapper;


import com.cjl.basic.zone.project.role.domain.ZRole;

import java.util.List;

public interface ZRoleMapper {
    /**
     * 批量删除
     * @param zRoleIds
     * @return
     */
    int deleteByPrimaryKey(Integer zRoleIds);

    /**
     * 插入
     * @param record
     * @return
     */
    int insertSelective(ZRole record);

    /**
     * 通过主键查询
     * @param roleId
     * @return
     */
    ZRole selectByPrimaryKey(Integer roleId);

    /**
     * 通过主键更新
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(ZRole record);

    /**
     * 查询角色列表
     *
     * @param role
     * @return
     */
    List<ZRole> selectRoleList(ZRole role);
}