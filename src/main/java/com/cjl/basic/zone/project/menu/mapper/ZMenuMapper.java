package com.cjl.basic.zone.project.menu.mapper;

import com.cjl.basic.zone.project.menu.domain.ZMenu;

import java.util.List;

public interface ZMenuMapper {
    int deleteByPrimaryKey(Integer menuId);

    int insertSelective(ZMenu record);

    ZMenu selectByPrimaryKey(Integer menuId);

    List<ZMenu> selectMenuListByRole(Integer roleId);

    int updateByPrimaryKeySelective(ZMenu record);

    int updateByPrimaryKey(ZMenu record);
}