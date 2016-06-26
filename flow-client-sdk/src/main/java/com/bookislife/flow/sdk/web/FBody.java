package com.bookislife.flow.sdk.web;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FBody{");
        sb.append("contentType='").append(contentType).append('\'');
        sb.append(", data=").append(new String(data, 0, data.length));
        sb.append(", inputStream=").append(inputStream);
        sb.append('}');
        return sb.toString();
    }
}
