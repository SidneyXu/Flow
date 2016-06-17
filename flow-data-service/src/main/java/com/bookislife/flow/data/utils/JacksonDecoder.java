package com.bookislife.flow.data.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by SidneyXu on 2016/05/23.
 */
public class JacksonDecoder {

    private static final Logger logger = LoggerFactory.getLogger(JacksonDecoder.class);

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
            logger.error("Unable to parse json String", e);
            throw new RuntimeException("Unable to parse json String: \n" + data);
        }
    }
}
