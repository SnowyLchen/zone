package com.cjl.basic.zone.project.menu.mapper;

import com.cjl.basic.zone.project.menu.domain.ZMenu;

import java.util.List;

public interface ZMenuMapper {
    /**
     * 删除
     *
     * @param menuId
     * @return
     */
    int deleteByPrimaryKey(Integer menuId);

    /**
     * 插入
     *
     * @param record
     * @return
     */
    int insertSelective(ZMenu record);

    /**
     * 通过id查询
     *
     * @param menuId
     * @return
     */
    ZMenu selectByPrimaryKey(Integer menuId);

    /**
     * 通过角色查询
     *
     * @param roleId
     * @return
     */
    List<ZMenu> selectMenuListByRole(Integer roleId);

    /**
     * 更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(ZMenu record);

    /**
     * 查询菜单列表
     *
     * @param menu
     * @return
     */
    List<ZMenu> selectMenuList(ZMenu menu);

    /**
     * 树
     * @param menu
     * @return
     */
    List<ZMenu> selectMenuTree(ZMenu menu);

    /**
     * 查询角色对应菜单权限
     * @param roleId
     * @return
     */
    List<ZMenu> checkArrMenuTree(Integer roleId);
}