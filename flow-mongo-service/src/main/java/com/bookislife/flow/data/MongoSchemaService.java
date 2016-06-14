package com.bookislife.flow.data;

import javax.inject.Inject;

/**
 * Created by SidneyXu on 2016/05/30.
 */
public class MongoSchemaService implements DBSchemaService {

    @Inject
    private MongoDao mongoDao;

    @Override
    public String insert(BaseSchema schema) {
        MongoEntity document = toDocument(schema);
        long now = System.currentTimeMillis();
        document.setCreatedAt(now);
        document.setUpdatedAt(now);
        return mongoDao.insert(SCHEMA_DATABASE_NAME, SCHEMA_TABLE_NAME, document);
    }

    @Override
    public BaseSchema get(String id) {
        MongoEntity document = (MongoEntity) mongoDao.findById(SCHEMA_DATABASE_NAME, SCHEMA_TABLE_NAME, id);
        return MongoSchema.toSchema(document);
    }

    private MongoEntity toDocument(BaseSchema schema) {
        if (schema instanceof MongoSchema) {
            return ((MongoSchema) schema).toDocument();
        }
        throw new IllegalStateException("MongoSchema is required, actual is " + schema.getClass().getName());
    }
}
