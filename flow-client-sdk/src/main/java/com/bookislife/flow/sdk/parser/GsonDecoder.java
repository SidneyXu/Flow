package com.bookislife.flow.sdk.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by SidneyXu on 2016/06/14.
 */
public class GsonDecoder extends JSONDecoder {

    private Gson gson;

    public GsonDecoder() {
        gson = new GsonBuilder()
                .create();
    }

    @Override
    protected <T> T internalDecode(String json, Class<T> type) {
        return gson.fromJson(json, type);
    }
}
