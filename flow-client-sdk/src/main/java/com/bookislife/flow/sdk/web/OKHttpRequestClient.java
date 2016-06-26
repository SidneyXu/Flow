package com.bookislife.flow.sdk.web;

import com.bookislife.flow.core.exception.FlowException;
import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by SidneyXu on 2016/06/13.
 */
public class OKHttpRequestClient extends RequestClient<Request, Response> {

    public static final int DEFAULT_TIMEOUT = 15000;

    private OkHttpClient client;

    public OKHttpRequestClient(OkHttpClient client) {
        this.client = client;
    }

    public OKHttpRequestClient() {
        client = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .build();
    }

    @Override
    public FResponse execute(FRequest fRequest) throws FlowException {
        Request request = getRequest(fRequest);
        Response response;
        try {
            response = client.newCall(request).execute();
            return getResponse(response);
        } catch (IOException e) {
            throw new FlowException("Unable to send request.", e);
        }
    }

    @Override
    protected Request getRequest(FRequest fRequest) throws FlowException {
        RequestBody body = null;
        if (fRequest.shouldHasBody()) {
            FBody fBody = fRequest.getBody();
            if (fBody != null) {
                body = RequestBody.create(MediaType.parse(fBody.getContentType()), fBody.getData());
            }
        }
        Request.Builder build = new Request.Builder()
                .url(fRequest.getUrl())
                .method(fRequest.getMethod(), body);
        if (fRequest.hasHeader()) {
            fRequest.getHeader().forEach(build::header);
        }
        return build.build();
    }


    @Override
    protected FResponse getResponse(Response response) throws FlowException {
        FResponse.Builder builder = FResponse.newBuilder();
        for (String name : response.headers().names()) {
            builder.addHeader(name, response.header(name));
        }
        builder.httpCode(response.code());
        ResponseBody body = response.body();
        if (null != body) {
            body.byteStream();
            builder.data(body.byteStream());
        }
        return builder.build();
    }

}