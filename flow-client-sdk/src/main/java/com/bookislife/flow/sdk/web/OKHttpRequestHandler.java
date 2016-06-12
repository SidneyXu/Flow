package com.bookislife.flow.sdk.web;

import okhttp3.*;

import java.io.IOException;

/**
 * Created by SidneyXu on 2016/06/13.
 */
public class OKHttpRequestHandler implements RequestHandler {

    private OkHttpClient client;

    public OKHttpRequestHandler(OkHttpClient client) {
        this.client = client;
    }

    public void getJson(FRequest request) {
        RequestBody body = null;
        if (request.shouldHasBody()) {
            FBody fBody = request.getBody();
            body = RequestBody.create(MediaType.parse(fBody.getContentType()), fBody.getData());
        }
        Request.Builder build = new Request.Builder()
                .url(request.getUrl())
                .method(request.getMethod(), body);
        if (request.hasHeader()) {
            request.getHeader().forEach(build::header);
        }
        try {
            Response response=client.newCall(build.build()).execute();
            // TODO: 16/6/13  
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
