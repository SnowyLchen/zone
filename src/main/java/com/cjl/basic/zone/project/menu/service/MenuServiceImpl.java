package com.cjl.basic.zone.project.menu.service;

import com.cjl.basic.zone.project.menu.domain.ZMenu;
import com.cjl.basic.zone.project.menu.mapper.ZMenuMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author chen
 */
@Service
public class MenuServiceImpl implements IMenuService {

    @Resource
    private ZMenuMapper menuMapper;

    @Override
    public List<ZMenu> selectMenuList(ZMenu menu) {
        return menuMapper.selectMenuList(menu);
    }

    @Override
    public ZMenu selectMenuById(Integer menuId) {
        return menuMapper.selectByPrimaryKey(menuId);
    }

    @Override
    public int addMenu(ZMenu menu) {
        return menuMapper.insertSelective(menu);
    }

    @Override
    public int editMenu(ZMenu menu) {
        return menuMapper.updateByPrimaryKey(menu);
    }

    @Override
    public int removeMenu(String id) {
        return 0;
    }
}
