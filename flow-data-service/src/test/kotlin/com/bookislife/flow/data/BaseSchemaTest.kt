package com.bookislife.flow.data

import org.junit.Test

/**
 * Created by SidneyXu on 2016/06/12.
 */
class BaseSchemaTest : BaseTest() {

    @Test
    fun test01() {
        val schema = BaseSchema("test_db", "test_table")
        schema.addColumn("name", ColumnType.String)
                .addColumn("age", ColumnType.Number)
                .addColumn("sex", ColumnType.Boolean)
                .addColumn("friend", ColumnType.Object)
        write(schema)

        /*
            {
              "databaseName" : "test_db",
              "tableName" : "test_table",
              "columnInfos" : {
                "sex" : "Boolean",
                "name" : "String",
                "friend" : "Object",
                "age" : "Number"
              }
            }
         */
    }
}