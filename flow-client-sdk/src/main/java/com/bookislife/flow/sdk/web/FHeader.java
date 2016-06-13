package com.bookislife.flow.sdk.web;

import java.util.HashMap;

/**
 * Created by SidneyXu on 2016/06/13.
 */
public class FHeader extends HashMap<String, String> {

    public void setContentType(String type) {
        put("Content-Type", type);
    }

    public String getContentType() {
        return get("Content-Type");
    }
}