package com.cjl.basic.zone.project.space.categories.service;

import com.cjl.basic.zone.project.space.categories.domain.ZCategories;

import java.util.List;

/**
 * 日志分类表(ZCategories)表服务接口
 *
 * @author makejava
 * @since 2021-05-04 19:46:48
 */
public interface IZCategoriesService {

    /**
     * [通过ID查询单条数据]
     *
     * @param cId [主键]
     * @return [对象实例]
     * @author xiaojie
     * @date 2021-05-04 19:46:48
     */
    ZCategories selectZCategoriesById(Integer cId);

    /**
     * [通过实体作为筛选条件查询]
     *
     * @param zCategories [实例对象]
     * @return [对象列表]
     * @author xiaojie
     * @date 2021-05-04 19:46:48
     */
    List<ZCategories> selectZCategoriesList(ZCategories zCategories);

    /**
     * [新增数据]
     *
     * @param zCategories [实例对象]
     * @return [影响行数]
     * @author xiaojie
     * @date 2021-05-04 19:46:48
     */
    int insertZCategories(ZCategories zCategories);

    /**
     * [修改数据]
     *
     * @param zCategories [实例对象]
     * @return [影响行数]
     * @author xiaojie
     * @date 2021-05-04 19:46:48
     */
    int updateZCategories(ZCategories zCategories);

    /**
     * [通过主键删除数据]
     *
     * @param cId [主键]
     * @return [影响行数]
     * @author xiaojie
     * @date 2021-05-04 19:46:48
     */
    int deleteZCategoriesById(Integer cId);

}
