package com.bookislife.flow.server.domain;

import com.bookislife.flow.core.Env;
import io.vertx.rxjava.ext.web.RoutingContext;

/**
 * Created by SidneyXu on 2016/06/23.
 */
public class RoutingContextWrapper extends RoutingContext {

    public RoutingContextWrapper(io.vertx.ext.web.RoutingContext delegate) {
        super(delegate);
    }

    public String getDatabaseName() {
        return request().getHeader(Env.Header.APPLICATION_ID);
    }

    public String getTableName() {
        return request().getParam("className");
    }

    public String getObjectId() {
        return request().getParam("objectId");
    }
}
