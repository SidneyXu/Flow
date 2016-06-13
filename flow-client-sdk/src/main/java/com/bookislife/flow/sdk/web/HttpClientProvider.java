package com.bookislife.flow.sdk.web;

/**
 * Created by SidneyXu on 2016/06/13.
 */
public class HttpClientProvider {

    private static RequestClient defaultClient;
    private static final Object lock = new Object();

    public static RequestClient provides(boolean newCreate) {
        if (newCreate) {
            return new OKHttpRequestClient();
        }
        synchronized (lock) {
            if (defaultClient == null) {
                defaultClient = new OKHttpRequestClient();
            }
            return defaultClient;
        }
    }

    public static RequestClient provides() {
        return provides(false);
    }
}
