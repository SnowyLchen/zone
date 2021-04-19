package com.cjl.basic.zone.project.role.service.impl;

import com.cjl.basic.zone.project.role.domain.ZRole;
import com.cjl.basic.zone.project.role.mapper.ZRoleMapper;
import com.cjl.basic.zone.project.role.service.IRoleService;
import org.springframework.stereotype.Service;

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
    public int removeRole(Integer id) {
        return roleMapper.deleteByPrimaryKey(id);
    }
}
