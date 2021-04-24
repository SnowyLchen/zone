package com.cjl.basic.zone.project.role.mapper;


import com.cjl.basic.zone.project.role.domain.ZRole;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 删除权限菜单
     * @param roleId
     * @return
     */
    int deleteRoleMenu(Integer roleId);

    /**
     * 添加权限菜单
     * @param roleId
     * @param menuId
     * @return
     */
    int insertRoleMenu(@Param("roleId") Integer roleId, @Param("menuId")Integer menuId);
}