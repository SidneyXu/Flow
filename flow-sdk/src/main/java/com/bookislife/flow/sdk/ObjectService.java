package com.bookislife.flow.sdk;

import com.bookislife.flow.core.domain.BaseEntity;

/**
 * Created by SidneyXu on 2016/06/16.
 */
public interface ObjectService {

    void save(BaseEntity entity);

    void delete(String id);
}
