package com.bookislife.flow.data.schema;

import com.bookislife.flow.data.ColumnType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SidneyXu on 2016/06/07.
 */
public class BaseSchema {

    public static final String FIELD_DATABASE_NAME = "database_name";
    public static final String FIELD_TABLE_NAME = "table_name";
    public static final String FIELD_COLUMNS_INFO = "columns_info";

    private String databaseName;
    private String tableName;
    private Map<String, ColumnType> columnInfos;

    public BaseSchema(String databaseName, String tableName) {
        this.databaseName = databaseName;
        this.tableName = tableName;
        columnInfos = new HashMap<>();
    }

    public BaseSchema addColumn(String name, ColumnType type) {
        columnInfos.put(name, type);
        return this;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getTableName() {
        return tableName;
    }

    public Map<String, ColumnType> getColumnInfos() {
        return columnInfos;
    }

    public void setColumnInfos(Map<String, ColumnType> columnInfos) {
        this.columnInfos = columnInfos;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BaseSchema{");
        sb.append("databaseName='").append(databaseName).append('\'');
        sb.append(", tableName='").append(tableName).append('\'');
        sb.append(", columnInfos=").append(columnInfos);
        sb.append('}');
        return sb.toString();
    }
}
