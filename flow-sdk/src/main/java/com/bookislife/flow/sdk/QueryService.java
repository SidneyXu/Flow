package com.bookislife.flow.sdk;

import com.bookislife.flow.core.domain.BaseEntity;
import com.bookislife.flow.data.BaseQuery;

import java.util.List;

/**
 * Created by SidneyXu on 2016/06/16.
 */
public interface QueryService<T extends BaseEntity> {

    T get(String id);

    List<T> find(BaseQuery query);
}
