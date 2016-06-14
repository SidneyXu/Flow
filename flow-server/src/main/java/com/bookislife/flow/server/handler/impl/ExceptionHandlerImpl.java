package com.bookislife.flow.server.handler.impl;

import com.bookislife.flow.core.exception.FlowException;
import com.bookislife.flow.server.handler.ExceptionHandler;
import com.bookislife.flow.server.utils.JacksonJsonBuilder;
import io.vertx.rxjava.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by SidneyXu on 2016/05/04.
 */
public class ExceptionHandlerImpl implements ExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(ExceptionHandlerImpl.class);

    @Override
    public void handle(RoutingContext context) {
        int statusCode = context.statusCode();
        int code = 0;
        String msg = "";
        Throwable throwable = context.failure();
        if (null != throwable) {
            msg = throwable.getMessage();
            if (throwable instanceof FlowException) {
                logger.error(msg, throwable);
                code = ((FlowException) throwable).errorCode;
            }
        }
        // TODO: 16/5/4 process status errorCode
        context.response()
                .setStatusCode(statusCode)
                .end(JacksonJsonBuilder.create()
                        .putNumber("errorCode", code)
                        .putString("errorMessage", msg)
                        .build());
    }
}
