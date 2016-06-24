package com.bookislife.flow.sdk.parser;

import com.bookislife.flow.core.exception.FlowException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by SidneyXu on 2016/06/14.
 */
public class JacksonParser extends JSONParser {

    private final ObjectMapper objectMapper;

    public JacksonParser() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Override
    protected <T> T internalDecode(String json, Class<T> type) {
        try {
            if (type.isAssignableFrom(FlowException.class)) {
                JsonNode node = objectMapper.readTree(json);
                return (T) new FlowException(node.get("errorCode").asInt(),
                        node.get("errorMessage").asText());
            }

            if (null == json) return null;
            return objectMapper.readValue(json, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected String internalEncode(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
