package com.bookislife.flow.core.utils;

/**
 * Created by SidneyXu on 2016/05/04.
 */
public interface JsonBuilder {

    String build();

    JsonBuilder put(String key, Object object);

    JsonBuilder putIfAbsent(String key, Object object);

    JsonBuilder putIfNotEmpty(String key, Object object);

    JsonBuilder putString(String key, String value);

    JsonBuilder putNumber(String key, Number number);

}
