package com.cjl.basic.zone.project.menu.service;


import com.cjl.basic.zone.project.menu.domain.ZMenu;
import com.cjl.basic.zone.project.menu.domain.ZMenuTree;

import java.util.List;

/**
 * @author chen
 */
public interface IMenuService {

    /**
     * 查询菜单列表
     *
     * @param menu
     * @return
     */
    List<ZMenu> selectMenuList(ZMenu menu);

    /**
     * 查询菜单通过id
     *
     * @param menuId
     * @return
     */
    ZMenu selectMenuById(Integer menuId);

    /**
     * 新增菜单
     *
     * @param menu
     * @return
     */
    int addMenu(ZMenu menu);

    /**
     * 修改菜单
     *
     * @param menu
     * @return
     */
    int editMenu(ZMenu menu);

    /**
     * 删除菜单
     *
     * @param id
     * @return
     */
    int removeMenu(String id);

    /**
     * 查询树
     *
     * @param menu
     * @return
     */
    List<ZMenuTree> selectMenuTree(ZMenu menu);
}
