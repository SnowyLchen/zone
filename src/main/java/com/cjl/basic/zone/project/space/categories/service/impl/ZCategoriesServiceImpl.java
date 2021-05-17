package com.cjl.basic.zone.project.space.categories.service.impl;

import com.cjl.basic.zone.common.utils.security.ShiroAuthenticateUtils;
import com.cjl.basic.zone.project.space.categories.domain.ZCategories;
import com.cjl.basic.zone.project.space.categories.mapper.ZCategoriesMapper;
import com.cjl.basic.zone.project.space.categories.service.IZCategoriesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 日志分类表(ZCategories)表服务实现类
 *
 * @author makejava
 * @since 2021-05-04 19:46:48
 */
@Service("zCategoriesService")
public class ZCategoriesServiceImpl implements IZCategoriesService {
    @Resource
    private ZCategoriesMapper zCategoriesMapper;

    /**
     * [通过ID查询单条数据]
     *
     * @param cId [主键]
     * @return [对象实例]
     * @author xiaojie
     * @date 2021-05-04 19:46:48
     */
    @Override
    public ZCategories selectZCategoriesById(Integer cId) {
        return this.zCategoriesMapper.selectZCategoriesById(cId);
    }

    /**
     * [通过实体作为筛选条件查询]
     *
     * @param zCategories [实例对象]
     * @return [对象列表]
     * @author xiaojie
     * @date 2021-05-04 19:46:48
     */
    @Override
    public List<ZCategories> selectZCategoriesList(ZCategories zCategories) {
        return this.zCategoriesMapper.selectZCategoriesList(zCategories);
    }

    /**
     * [新增数据]
     *
     * @param zCategories [实例对象]
     * @return [影响行数]
     * @author xiaojie
     * @date 2021-05-04 19:46:48
     */
    @Override
    public int insertZCategories(ZCategories zCategories) {
        zCategories.setAccountId(ShiroAuthenticateUtils.getAccountId());
        return this.zCategoriesMapper.insertZCategories(zCategories);
    }

    /**
     * [修改数据]
     *
     * @param zCategories [实例对象]
     * @return [影响行数]
     * @author xiaojie
     * @date 2021-05-04 19:46:48
     */
    @Override
    public int updateZCategories(ZCategories zCategories) {
        return this.zCategoriesMapper.updateZCategories(zCategories);
    }

    /**
     * [通过主键删除数据]
     *
     * @param cId [主键]
     * @return [影响行数]
     * @author xiaojie
     * @date 2021-05-04 19:46:48
     */
    @Override
    public int deleteZCategoriesById(Integer cId) {
        return this.zCategoriesMapper.deleteZCategoriesById(cId);
    }
}
