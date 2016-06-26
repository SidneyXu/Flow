package com.bookislife.flow.sdk;

import com.bookislife.flow.core.domain.BaseEntity;
import com.bookislife.flow.core.exception.FlowException;
import com.bookislife.flow.data.BaseModifier;
import com.bookislife.flow.data.BaseQuery;

import java.util.List;

/**
 * Created by SidneyXu on 2016/06/16.
 */
public interface ObjectService {

    void save(String type, BaseEntity entity) throws FlowException;

    int delete(String type, String id) throws FlowException;

    BaseEntity get(String type, String id) throws FlowException;

    <T extends BaseEntity> T get(Class<T> clazz, String type, String id) throws FlowException;

    List<? extends BaseEntity> find(BaseQuery query) throws FlowException;

    int update(String type, String id, BaseModifier modifier) throws FlowException;
}
