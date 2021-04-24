package com.cjl.basic.zone.project.role.service;

import com.cjl.basic.zone.project.role.domain.ZRole;

import java.util.List;

/**
 * @author chen
 */
public interface IRoleService {
    /**
     * 查询角色列表
     *
     * @param role
     * @return
     */
    List<ZRole> selectRoleList(ZRole role);

    /**
     * 查询角色通过id
     *
     * @param roleId
     * @return
     */
    ZRole selectRoleById(Integer roleId);

    /**
     * 新增角色
     *
     * @param role
     * @return
     */
    int addRole(ZRole role);

    /**
     * 修改角色
     *
     * @param role
     * @return
     */
    int editRole(ZRole role);

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    int removeRole(String id);

    /**
     * 修改角色权限
     * @param roleId
     * @param menuIds
     * @return
     */
    int editRoleMenu(Integer roleId, String menuIds);
}
