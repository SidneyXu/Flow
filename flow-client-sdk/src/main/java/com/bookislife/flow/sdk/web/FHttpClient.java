package com.bookislife.flow.sdk.web;

import com.bookislife.flow.core.exception.FlowException;
import com.bookislife.flow.sdk.parser.JSONParser;

import java.io.InputStream;

/**
 * Created by SidneyXu on 2016/06/13.
 */
public class FHttpClient {

    public String requestJson(FRequest request) throws FlowException {
        FResponse response = HttpClientProvider.provides().execute(request);
        handleFailure(response);
        return response.getBodyAsString();
    }

    public InputStream requestStream(FRequest request) throws FlowException {
        FResponse response = HttpClientProvider.provides().execute(request);
        handleFailure(response);
        return response.getBodyAsStream();
    }

    protected void handleFailure(FResponse response) throws FlowException {
        if (!response.isSuccessful()) {
            if (response.isJsonResponse()) {
                String json = response.getBodyAsString();
                throw JSONParser.decode(json, FlowException.class);
            }
            throw new FlowException(response.getStatusCode(), response.getBodyAsString());
        }
    }
}
