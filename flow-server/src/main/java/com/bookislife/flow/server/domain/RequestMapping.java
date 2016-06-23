package com.bookislife.flow.server.domain;

import com.bookislife.flow.core.Env;
import com.bookislife.flow.core.exception.FlowException;
import com.google.common.base.Strings;
import io.vertx.rxjava.core.http.HttpServerRequest;
import io.vertx.rxjava.ext.web.RoutingContext;

/**
 * Created by SidneyXu on 2016/06/23.
 */
public class RequestMapping {

    private final RoutingContext context;
    private final HttpServerRequest request;

    public RequestMapping(RoutingContext context) {
        this.context = context;
        request = context.request();
    }

    public String getDatabaseName() throws FlowException {
        String applicationId = request.getHeader(Env.Header.APPLICATION_ID);
        if (Strings.isNullOrEmpty(applicationId)) {
            throw new FlowException(FlowException.ILLEGAL_ARGUMENTS, "param [applicationId] in header is required");
        }
        return applicationId;
    }

    public String getTableName() throws FlowException {
        String tableName = request.getParam("className");
        if (Strings.isNullOrEmpty(tableName)) {
            throw new FlowException(FlowException.ILLEGAL_ARGUMENTS, "param [className] is required");
        }
        return tableName;
    }

    public String getBodyAsString() throws FlowException {
        return context.getBodyAsString();
    }

    public String getObjectId() throws FlowException {
        String objectId = request.getParam("objectId");
        if (Strings.isNullOrEmpty(objectId)) {
            throw new FlowException(FlowException.ILLEGAL_ARGUMENTS, "param [objectId] is required");
        }
        return objectId;
    }
}
