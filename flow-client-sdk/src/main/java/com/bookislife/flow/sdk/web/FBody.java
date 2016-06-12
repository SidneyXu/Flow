package com.bookislife.flow.sdk.web;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by SidneyXu on 2016/06/13.
 */
public class FBody {

    private String contentType;
    private byte[] data;
    private InputStream inputStream;

    public static FBody fromJson(String json) {
        FBody body = new FBody();
        body.contentType = "application/json";
        try {
            body.data = json.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return body;
    }

    byte[] getData() {
        return data;
    }

    public String getContentType() {
        return contentType;
    }
}
