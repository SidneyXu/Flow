package com.bookislife.flow.data;

import com.bookislife.flow.core.domain.BaseEntity;
import com.bookislife.flow.core.exception.FlowException;
import com.bookislife.flow.data.utils.JacksonDecoder;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SidneyXu on 2016/05/19.
 */
public class MongoDBStorage implements DBStorage {

    @Inject
    private MongoDao mongoDao;

    @Override
    public BaseEntity findById(String database, String tableName, String id) {
        return mongoDao.findById(database, tableName, id);
    }

    @Override
    public List<BaseEntity> findAll(String database, String tableName, String query) throws FlowException {
        MongoQuery mongoQuery = JacksonDecoder.decode(query, MongoQuery.class);
        return mongoDao.findAll(database, tableName, mongoQuery);
    }

    @Override
    public BaseEntity insert(String database, String tableName, String data) {
        MongoDocument document = JacksonDecoder.decode(data, MongoDocument.class);
        long current = System.currentTimeMillis();
        document.setCreatedAt(current);
        document.setUpdatedAt(current);
        String id = mongoDao.insert(database, tableName, document);
        document.setId(id);
        return document;
    }

    @Override
    public int update(String database, String tableName, String query, String modifier) throws FlowException {
        MongoQuery mongoQuery = JacksonDecoder.decode(query, MongoQuery.class);
        BaseModifier mongoModifier = JacksonDecoder.decode(modifier, BaseModifier.class);
        if (mongoModifier != null) {
            Map<String, Object> newUpdater = new HashMap<>();
            newUpdater.put(BaseEntity.FIELD_UPDATED_AT, System.currentTimeMillis());
            mongoModifier.modifier(BaseModifier.SET, newUpdater);
        }
        return mongoDao.update(database, tableName, mongoQuery, mongoModifier);
    }

    @Override
    public int delete(String database, String tableName, String id) {
        return mongoDao.deleteById(database, tableName, id);
    }

    @Override
    public int deleteAll(String database, String tableName, String query) throws FlowException {
        MongoQuery mongoQuery = JacksonDecoder.decode(query, MongoQuery.class);
        return mongoDao.deleteAll(database, tableName, mongoQuery);
    }

    @Override
    public long count(String database, String tableName, String query) {
        MongoQuery mongoQuery = JacksonDecoder.decode(query, MongoQuery.class);
        return mongoDao.count(database, tableName, mongoQuery);
    }
}