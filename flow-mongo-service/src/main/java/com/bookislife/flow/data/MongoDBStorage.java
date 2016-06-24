package com.bookislife.flow.data;

import com.bookislife.flow.core.domain.BaseEntity;
import com.bookislife.flow.core.exception.FlowException;
import com.bookislife.flow.data.utils.JacksonDecoder;
import com.bookislife.flow.data.utils.QueryValidator;

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

        // TODO: 6/24/16  
        return mongoDao.findById(database, tableName, id);
    }

    @Override
    public List<BaseEntity> findAll(String database, String tableName, String query) throws FlowException {
        MongoQuery mongoQuery = JacksonDecoder.decode(query, MongoQuery.class);
        if (mongoQuery != null) {
            QueryValidator.validate(mongoQuery.getCondition());
        }
        return mongoDao.findAll(database, tableName, mongoQuery);
    }

    @Override
    public BaseEntity insert(String database, String tableName, String data) throws FlowException {
        MongoEntity document = JacksonDecoder.decode(data, MongoEntity.class);
        long current = System.currentTimeMillis();
        document.setCreatedAt(current);
        document.setUpdatedAt(current);
        String id = mongoDao.insert(database, tableName, document);
        document.setId(id);
        return document;
    }

    @Override
    public BaseEntity insert(String database, String tableName, BaseEntity entity) throws FlowException {
        MongoEntity document = new MongoEntity(entity.getData());
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
    public int updateById(String database, String tableName, String id, String modifier) throws FlowException {
        BaseModifier mongoModifier = JacksonDecoder.decode(modifier, BaseModifier.class);
        if (mongoModifier != null) {
            Map<String, Object> newUpdater = new HashMap<>();
            newUpdater.put(BaseEntity.FIELD_UPDATED_AT, System.currentTimeMillis());
            mongoModifier.modifier(BaseModifier.SET, newUpdater);
        }
        return mongoDao.update(database, tableName, id, mongoModifier);
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
        if (tableName == null && mongoQuery != null) {
            tableName = mongoQuery.getTableName();
        }
        return mongoDao.count(database, tableName, mongoQuery);
    }
}
