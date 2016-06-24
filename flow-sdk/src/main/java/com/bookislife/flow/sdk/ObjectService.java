package com.bookislife.flow.sdk;

import com.bookislife.flow.core.domain.BaseEntity;
import com.bookislife.flow.core.exception.FlowException;

/**
 * Created by SidneyXu on 2016/06/16.
 */
public interface ObjectService {

    void save(String type, BaseEntity entity) throws FlowException;

    void delete(String id);

    BaseEntity get(String type, String id) throws FlowException;
}
