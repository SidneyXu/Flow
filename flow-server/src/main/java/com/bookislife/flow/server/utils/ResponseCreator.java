package com.bookislife.flow.server.utils;


import com.bookislife.flow.core.domain.BaseEntity;

/**
 * Created by SidneyXu on 2016/05/23.
 */
public class ResponseCreator {

    public static final String FIELD_COUNT = "count";

    public static String newCreateResponse(BaseEntity entity) {
        return JacksonJsonBuilder.create()
                .put(BaseEntity.FIELD_ID, entity.getId())
                .put(BaseEntity.FIELD_CREATED_AT, entity.getCreatedAt())
                .build();
    }

    public static String newQueryResponse(BaseEntity entity) {
        return JacksonJsonBuilder.create()
                .put(BaseEntity.FIELD_DATA, entity)
                .put(FIELD_COUNT, 1)
                .build();
    }
}
