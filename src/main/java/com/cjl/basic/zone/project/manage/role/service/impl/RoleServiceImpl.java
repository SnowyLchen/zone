package com.cjl.basic.zone.project.manage.role.service.impl;

import com.cjl.basic.zone.common.support.Convert;
import com.cjl.basic.zone.project.manage.role.domain.ZRole;
import com.cjl.basic.zone.project.manage.role.mapper.ZRoleMapper;
import com.cjl.basic.zone.project.manage.role.service.IRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author chen
 */
@Service
public class RoleServiceImpl implements IRoleService {

    @Resource
    private ZRoleMapper roleMapper;

    @Override
    public List<ZRole> selectRoleList(ZRole role) {
        return roleMapper.selectRoleList(role);
    }

    @Override
    public ZRole selectRoleById(Integer roleId) {
        return roleMapper.selectByPrimaryKey(roleId);
    }

    @Override
    public int addRole(ZRole role) {
        // 默认是停用的
        role.setStatus("0");
        return roleMapper.insertSelective(role);
    }

    @Override
    public int editRole(ZRole role) {
        return roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeRole(String id) {
        for (Integer roleId : Convert.toIntArray(id)) {
            roleMapper.deleteByPrimaryKey(roleId);
        }
        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int editRoleMenu(Integer roleId, String menuIds) {
        // 先删除之前所有权限
        roleMapper.deleteRoleMenu(roleId);
        // 添加权限菜单
        for (Integer menuId : Convert.toIntArray(menuIds)) {
            roleMapper.insertRoleMenu(roleId, menuId);
        }
        return 1;
    }
}
