package com.cjl.basic.zone.common.DeleteUtils.service;

import com.cjl.basic.zone.common.DeleteUtils.domain.TableInformation;

import java.util.List;

/**
 * 逻辑删除帮助类
 *
 * @author xh
 */
public interface DeleteService {
    /**
     * 根据表名及其列名，查询相关数据库表
     *
     * @param tableInfo 表和列信息
     * @return 数据库表列表
     */
    public List<TableInformation> selectTableInfo(TableInformation tableInfo);


    /**
     * 根据表名、列名、列值，查询是否引用该条数据
     *
     * @param tableInfo 表名、列名、列值信息
     * @return 应用基础数据条数
     */
    public Integer selectCountByColumn(TableInformation tableInfo);


    /**
     * <p>基本数据逻辑删除时，判断是否在其他地方引用
     * <p>如果被使用抛出异常，并提示相关信息
     *
     * @param tableInfo 信息
     * @return 结果
     */
    Integer deleteInterceptor(TableInformation tableInfo);
}
