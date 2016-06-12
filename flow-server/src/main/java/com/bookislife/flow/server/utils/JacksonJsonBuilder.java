package com.bookislife.flow.server.utils;

import com.bookislife.flow.core.utils.JsonBuilder;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Map;

/**
 * Created by SidneyXu on 2016/05/04.
 */
public class JacksonJsonBuilder implements JsonBuilder {

    private final static ObjectMapper objectMapper = new ObjectMapper();
    private JsonGenerator generator;
    private StringWriter writer;

    public JacksonJsonBuilder() {
        try {
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            writer = new StringWriter();
            generator = objectMapper.getFactory()
                    .createGenerator(writer);
            generator.writeStartObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static JacksonJsonBuilder create() {
        JacksonJsonBuilder builder = new JacksonJsonBuilder();
        return builder;
    }

    public String build() {
        try {
            generator.writeEndObject();
            generator.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return writer.toString();
    }

    public JacksonJsonBuilder put(String key, Object object) {
        try {
            generator.writeObjectField(key, object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public JacksonJsonBuilder putIfAbsent(String key, Object object) {
        try {
            if (null == object) {
                return this;
            }
            generator.writeObjectField(key, object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public JacksonJsonBuilder putIfNotEmpty(String key, Object object) {
        try {
            if (null == object) {
                return this;
            }
            if (object instanceof String) {
                if (((String) object).trim().equals("")) {
                    return this;
                }
            } else if (object instanceof Collection) {
                if (((Collection) object).size() == 0) {
                    return this;
                }
            } else if (object instanceof Map) {
                if (((Map) object).size() == 0) {
                    return this;
                }
            }

            generator.writeObjectField(key, object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public JacksonJsonBuilder putString(String key, String value) {
        try {
            generator.writeStringField(key, value);
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return this;
    }

    public JacksonJsonBuilder putNumber(String key, Number number) {
        try {
            if (number instanceof Long) {
                generator.writeNumberField(key, number.longValue());
            } else if (number instanceof Integer) {
                generator.writeNumberField(key, number.intValue());
            } else if (number instanceof Double) {
                generator.writeNumberField(key, number.doubleValue());
            } else if (number instanceof Float) {
                generator.writeNumberField(key, number.floatValue());
            } else {
                throw new IllegalArgumentException("type mismatch; found : " + number.getClass().getName() + " required: Integer, Long, Float or Double.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }
}
