package com.cjl.basic.zone.project.role.mapper;


import com.cjl.basic.zone.project.role.domain.ZRole;

import java.util.List;

public interface ZRoleMapper {
    int deleteByPrimaryKey(Integer zRoleId);

    int insertSelective(ZRole record);

    ZRole selectByPrimaryKey(Integer zRoleId);

    int updateByPrimaryKeySelective(ZRole record);
    /**
     * 查询角色列表
     * @param role
     * @return
     */
    List<ZRole> selectRoleList(ZRole role);
}