package com.bookislife.flow.sdk;

import com.bookislife.flow.data.BaseDao;
import com.bookislife.flow.data.MongoContext;
import com.bookislife.flow.data.MongoDao;

/**
 * Created by SidneyXu on 2016/06/16.
 */
public class FlowApi {

    public int port;
    public String applicationId;

    private ObjectService objectService;

    private FlowApi(String applicationId, int port) {
        this.applicationId = applicationId;
        this.port = port;
        config();
    }

    private void config() {
        // TODO: 16/6/26
        MongoContext context = new MongoContext();
        BaseDao dao = new MongoDao(context);
        objectService = new ObjectServiceImpl(this, dao);
    }

    public ObjectService getObjectService() {
        return objectService;
    }


    public static class Builder {
        private String applicationId;
        private int port;

        public Builder applicationId(String applicationId) {
            this.applicationId = applicationId;
            return this;
        }

        public Builder port(int port) {
            this.port = port;
            return this;
        }

        public FlowApi build() {
            return new FlowApi(applicationId, port);
        }
    }
}
