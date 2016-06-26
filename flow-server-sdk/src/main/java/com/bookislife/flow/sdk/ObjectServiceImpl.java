package com.bookislife.flow.sdk;

import com.bookislife.flow.core.domain.BaseEntity;
import com.bookislife.flow.core.exception.FlowException;
import com.bookislife.flow.data.BaseDao;
import com.bookislife.flow.data.BaseModifier;
import com.bookislife.flow.data.BaseQuery;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by SidneyXu on 2016/06/16.
 */
public class ObjectServiceImpl implements ObjectService {

    private FlowApi api;

    @Inject
    private BaseDao dao;

    private String databaseName;

    public ObjectServiceImpl(FlowApi api, BaseDao dao) {
        this.api = api;
        databaseName = api.applicationId;
        this.dao = dao;
    }

    @Override
    public void save(String type, BaseEntity entity) throws FlowException {
        dao.insert(databaseName, type, entity);
    }

    @Override
    public int delete(String type, String id) throws FlowException {
        return 0;
    }

    @Override
    public BaseEntity get(String type, String id) throws FlowException {
        return null;
    }

    @Override
    public <T extends BaseEntity> T get(Class<T> clazz, String type, String id) throws FlowException {
        return null;
    }

    @Override
    public List<? extends BaseEntity> find(BaseQuery query) throws FlowException {
        return null;
    }

    @Override
    public int update(String type, String id, BaseModifier modifier) throws FlowException {
        return 0;
    }

}
