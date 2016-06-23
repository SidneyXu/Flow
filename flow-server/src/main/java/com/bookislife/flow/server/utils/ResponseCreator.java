package com.bookislife.flow.server.utils;

import com.bookislife.flow.core.domain.BaseEntity;
import com.bookislife.flow.core.exception.FlowException;

import java.util.HashMap;
import java.util.List;

/**
 * Created by SidneyXu on 2016/05/23.
 */
public class ResponseCreator {

    public static final String FIELD_COUNT = "count";
    public static final String FIELD_ERROR_CODE = "error_code";
    public static final String FIELD_ERROR_MESSAGE = "error_message";
    public static final String FIELD_DATAS = "datas";

    public static String newCreateResponse(BaseEntity entity) {
        return JacksonJsonBuilder.create()
                .put(BaseEntity.FIELD_ID, entity.getId())
                .put(BaseEntity.FIELD_CREATED_AT, entity.getCreatedAt())
                .build();
    }

    public static String newDeleteResponse(int n) {
        if (n == 0) {
            return JacksonJsonBuilder.create()
                    .put(FIELD_ERROR_CODE, FlowException.OBJECT_NOT_FOUND)
                    .put(FIELD_ERROR_MESSAGE, "Object not found for deleting.")
                    .build();
        }
        return JacksonJsonBuilder.create()
                .put(FIELD_COUNT, n)
                .build();
    }

    public static String newCountResponse(long n) {
        return JacksonJsonBuilder.create()
                .put(FIELD_COUNT, n)
                .build();
    }

    public static String newQueryResponse(BaseEntity entity) {
        if (null == entity) {
            return JacksonJsonBuilder.create()
                    .put(FIELD_ERROR_CODE, FlowException.OBJECT_NOT_FOUND)
                    .put(FIELD_ERROR_MESSAGE, "Object not found.")
                    .build();
        }
        return JacksonJsonBuilder.create()
                .put(BaseEntity.FIELD_DATA, entity.getData())
                .put(FIELD_COUNT, 1)
                .build();
    }

    public static String newQueryResponse(List<? extends BaseEntity> entities) {
        if (null == entities || entities.isEmpty()) {
            return JacksonJsonBuilder.create()
                    .put(FIELD_ERROR_CODE, FlowException.OBJECT_NOT_FOUND)
                    .put(FIELD_ERROR_MESSAGE, "Object not found.")
                    .build();
        } else {
            Object[] arrays = entities.stream().map(o ->
                    new HashMap<String, Object>() {{
                        put(BaseEntity.FIELD_DATA, o.getData());
                    }}).toArray();
            return JacksonJsonBuilder.create()
                    .put(FIELD_COUNT, entities.size())
                    .put(FIELD_DATAS, arrays)
                    .build();
        }
    }

    public static String newErrorResponse(int code, String message) {
        return JacksonJsonBuilder.create()
                .put(FIELD_ERROR_CODE, code)
                .put(FIELD_ERROR_MESSAGE, message)
                .build();
    }

    public static String newErrorResponse(String message) {
        return JacksonJsonBuilder.create()
                .put(FIELD_ERROR_CODE, FlowException.UNKNOWN_ERROR)
                .put(FIELD_ERROR_MESSAGE, message)
                .build();
    }

    public static String newErrorResponse(FlowException e) {
        return JacksonJsonBuilder.create()
                .put(FIELD_ERROR_CODE, e.errorCode)
                .put(FIELD_ERROR_MESSAGE, e.errorMessage)
                .build();
    }
}
