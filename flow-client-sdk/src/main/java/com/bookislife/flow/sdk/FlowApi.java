package com.bookislife.flow.sdk;

import com.bookislife.flow.sdk.web.HttpClientProvider;
import com.bookislife.flow.sdk.web.RequestClient;

/**
 * Created by SidneyXu on 2016/06/13.
 */
public class FlowApi {

    public final String applicationId;
    public final String targetUrl;

    private RequestClient requestClient;
    private ObjectService objectService;

    private FlowApi(String applicationId, String targetUrl) {
        requestClient = HttpClientProvider.provides();
        this.applicationId = applicationId;
        this.targetUrl = targetUrl;
        config();
    }

    private void config() {
        objectService = new ObjectServiceImpl(this, requestClient);
    }

    public ObjectService getObjectService() {
        return objectService;
    }

    public static class Builder {
        private String applicationId;
        private String targetUrl;

        public Builder applicationId(String applicationId) {
            this.applicationId = applicationId;
            return this;
        }

        public Builder targetServer(String host, int port) {
            this.targetUrl = host + ":" + port;
            return this;
        }

        public Builder targetServer(String targetUrl) {
            this.targetUrl = targetUrl;
            return this;
        }

        public FlowApi build() {
            return new FlowApi(applicationId, targetUrl);
        }
    }
}
