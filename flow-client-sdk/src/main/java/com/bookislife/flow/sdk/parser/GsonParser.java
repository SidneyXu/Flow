package com.bookislife.flow.sdk.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by SidneyXu on 2016/06/14.
 */
public class GsonParser extends JSONParser {

    private Gson gson;

    public GsonParser() {
        gson = new GsonBuilder()
                .create();
    }

    @Override
    protected <T> T internalDecode(String json, Class<T> type) {
        return gson.fromJson(json, type);
    }

    @Override
    protected String internalEncode(Object object) {
        return gson.toJson(object);
    }
}
