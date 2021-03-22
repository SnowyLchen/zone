package com.cjl.basic.zone.common.DeleteUtils.service;


import com.cjl.basic.zone.common.DeleteUtils.domain.TableInformation;
import com.cjl.basic.zone.common.DeleteUtils.mapper.TableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * 逻辑删除帮助类实现
 *
 * @author xh
 */
@Service
public class DeleteServiceImpl implements DeleteService {

    @Autowired
    private TableMapper tableMapper;


    /**
     * 根据表名及其列名，查询相关数据库表
     *
     * @param tableInfo 表和列信息
     * @return 数据库表列表
     */
    @Override
    public List<TableInformation> selectTableInfo(TableInformation tableInfo) {
        return tableMapper.selectTableInfo(tableInfo);
    }


    /**
     * 根据表名、列名、列值，查询是否引用该条数据
     *
     * @param tableInfo 表信息
     * @return 应用基础数据条数
     */
    @Override
    public Integer selectCountByColumn(TableInformation tableInfo) {
        return tableMapper.selectCountByColumn(tableInfo);
    }

    /**
     * <p>基本数据逻辑删除时，判断是否在其他地方引用
     * <p>如果被使用抛出异常，并提示相关信息
     *
     * @param tableInfo 信息
     * @return 结果
     */
    @Override
    public Integer deleteInterceptor(TableInformation tableInfo) {
        int aa = 0;
        List<TableInformation> tableInfos = tableMapper.selectTableInfo(tableInfo);
        for (TableInformation info : tableInfos) {
            tableInfo.setTableName(info.getTableName());
            Integer code = tableMapper.selectCountByColumn(tableInfo);
            if (code > 0) {
                aa++;
            }
        }
        return aa;
    }

}
