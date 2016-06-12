package com.bookislife.flow.data;

/**
 * Created by SidneyXu on 2016/05/29.
 */
public interface DBSchemaService {

    String SCHEMA_DATABASE_NAME = "schema";
    String SCHEMA_TABLE_NAME = "flow_data_schema";

    String insert(BaseSchema schema);

    BaseSchema get(String id);


}
