package com.bookislife.flow.sdk;

import com.bookislife.flow.core.domain.BaseEntity;
import com.bookislife.flow.core.exception.FlowException;
import com.bookislife.flow.data.DBStorage;

import javax.inject.Inject;

/**
 * Created by SidneyXu on 2016/06/16.
 */
public class ObjectServiceImpl implements ObjectService {

    @Inject
    private DBStorage storage;

    private String databaseName;

    public ObjectServiceImpl() {
        FlowApi api = FlowApi.getInstance();
        databaseName = api.applicationId;
    }

    @Override
    public void save(String type, BaseEntity entity) throws FlowException {
        storage.insert(databaseName,type,entity);
    }

    @Override
    public void delete(String id) {

    }
}
