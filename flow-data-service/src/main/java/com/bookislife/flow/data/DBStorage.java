package com.bookislife.flow.data;

import com.bookislife.flow.core.domain.BaseEntity;
import com.bookislife.flow.core.exception.FlowException;
import com.bookislife.flow.core.utils.Pair;

import java.util.List;

/**
 * Created by SidneyXu on 2016/05/19.
 */
public interface DBStorage {

    BaseEntity findById(String database,
                        String tableName,
                        String id) throws FlowException;

    List<BaseEntity> findAll(String database, String tableName, String query) throws FlowException;

    BaseEntity insert(String database, String tableName, String data) throws FlowException;

    BaseEntity insert(String database, String tableName, BaseEntity entity) throws FlowException;

    int update(String database, String tableName, String query, String modifier) throws FlowException;

    Pair<Integer, Long> updateById(String database, String tableName, String id, String modifier) throws FlowException;

    int delete(String database, String tableName, String id) throws FlowException;

    int deleteAll(String database, String tableName, String query) throws FlowException;

    long count(String database, String tableName, String query) throws FlowException;
}
