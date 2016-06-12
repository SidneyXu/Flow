package com.bookislife.flow.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by SidneyXu on 2016/05/06.
 */
public class MongoSchema extends BaseSchema {

    public MongoSchema(String databaseName, String tableName) {
        super(databaseName, tableName);
    }

    public MongoDocument toDocument() {
        Map<String, Object> map = new HashMap<>();
        map.put(FIELD_DATABASE_NAME, getDatabaseName());
        map.put(FIELD_TABLE_NAME, getTableName());
        map.put(FIELD_COLUMNS_INFO, convertColumnInfos());
        return new MongoDocument(Collections.unmodifiableMap(map));
    }

    public static MongoSchema toSchema(MongoDocument mongoDocument) {
        Map<String, Object> data = mongoDocument.getData();
        String databaseName = (String) data.get(FIELD_DATABASE_NAME);
        String tableName = (String) data.get(FIELD_TABLE_NAME);
        MongoSchema schema = new MongoSchema(databaseName, tableName);
        schema.setColumnInfos(convertColumnInfos((Map<String,Object>)data.get(FIELD_COLUMNS_INFO)));
        return schema;
    }

    private static Map<String, ColumnType> convertColumnInfos(Map<String,Object> map) {
        Map<String, ColumnType> infos = new HashMap<>();
        map.forEach((s, type) -> {
            infos.put(s, ColumnType.valueOf((String) type));
        });
        return infos;
    }

    private Map<String, String> convertColumnInfos() {
        Map<String, String> infos = new HashMap<>();
        getColumnInfos().forEach((s, type) -> {
            infos.put(s, type.name());
        });
        return infos;
    }
}
