package com.cjl.basic.zone.common.DeleteUtils.mapper;


import com.cjl.basic.zone.common.DeleteUtils.domain.TableInformation;

import java.util.List;

/**
 * 查询数据库信息
 *
 * @author xh
 */
public interface TableMapper {

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


}
