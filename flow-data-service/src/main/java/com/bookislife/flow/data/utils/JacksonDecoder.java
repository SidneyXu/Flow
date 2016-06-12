package com.bookislife.flow.data.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by SidneyXu on 2016/05/23.
 */
public class JacksonDecoder {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static <T> T decode(String data, Class<T> type) {
        try {
            if (null == data) return null;
            return objectMapper.readValue(data, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
