package com.bookislife.flow.sdk.web;

import com.bookislife.flow.core.exception.FlowException;

/**
 * Created by SidneyXu on 2016/06/13.
 */
public class FHttpClient {

    public void getJson(FRequest request) throws FlowException {
        FResponse response = HttpClientProvider.provides().execute(request);
        if (!response.isSuccessful()) {
            if (response.isJsonResponse()) {
                String json = response.getBodyAsString();
                //TODO
            } else {

            }
        }
    }


}
