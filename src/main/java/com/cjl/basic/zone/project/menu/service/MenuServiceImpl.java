package com.cjl.basic.zone.project.menu.service;

import com.cjl.basic.zone.common.support.Convert;
import com.cjl.basic.zone.common.utils.StringFormat;
import com.cjl.basic.zone.project.menu.domain.ZMenu;
import com.cjl.basic.zone.project.menu.domain.ZMenuTree;
import com.cjl.basic.zone.project.menu.mapper.ZMenuMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return menuMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeMenu(String id) {
        for (Integer menuId : Convert.toIntArray(id)) {
            menuMapper.deleteByPrimaryKey(menuId);
        }
        return 1;
    }

    @Override
    public List<ZMenuTree> selectMenuTree(ZMenu menu) {
        return StringFormat.formatTreeData(menuMapper.selectMenuTree(menu));
    }

    @Override
    public List<ZMenuTree> checkArrMenuTree(ZMenu menu) {
        return StringFormat.formatTreeData(menuMapper.checkArrMenuTree(menu));
    }
}
