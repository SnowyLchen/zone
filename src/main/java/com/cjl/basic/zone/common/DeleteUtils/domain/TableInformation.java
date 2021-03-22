package com.cjl.basic.zone.common.DeleteUtils.domain;


import com.cjl.basic.zone.framework.web.domain.BaseEntity;

/**
 * 数据库表
 *
 * @author xh
 */
public class TableInformation extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 表名称
     */
    private String tableName;


    /**
     * 数据库名称
     */
    private String tableSchema;


    /**
     * 列名
     */
    private String columnName;

    /**
     * 列值
     */
    private Integer[] columnValue;

    public TableInformation() {

    }

    public TableInformation(String tableName, String columnName, Integer[] columnValue) {
        this.tableName = tableName;
        this.columnName = columnName;
        this.columnValue = columnValue;
    }

    public TableInformation(String tableName, String tableSchema, String columnName, Integer[] columnValue) {
        this.tableName = tableName;
        this.tableSchema = tableSchema;
        this.columnName = columnName;
        this.columnValue = columnValue;
    }

    public Integer[] getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(Integer[] columnValue) {
        this.columnValue = columnValue;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
}
