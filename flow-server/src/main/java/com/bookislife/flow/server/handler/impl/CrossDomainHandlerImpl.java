package com.bookislife.flow.server.handler.impl;

import com.bookislife.flow.core.Env;
import com.bookislife.flow.server.handler.CrossDomainHandler;
import com.google.common.base.Joiner;
import io.vertx.rxjava.ext.web.RoutingContext;

import java.util.Locale;

/**
 * Created by SidneyXu on 2016/06/02.
 */
public class CrossDomainHandlerImpl implements CrossDomainHandler {
    @Override
    public void handle(RoutingContext context) {
        if (context.request().headers().contains("User-Agent")) {
            String agent = context.request().headers().get("User-Agent");
            String agentUppercase = agent.toUpperCase(Locale.US);
            if (!agentUppercase.contains("IOS") && !agentUppercase.contains("ANDROID")) {
                context.addHeadersEndHandler(event -> {
                    context.response().headers().add("Access-Control-Allow-Origin", "*");
                    String allowHeaders = Joiner.on(",").join(Env.Header.APPLICATION_ID, Env.Header.SESSION_TOKEN,
                            Env.Header.VERSION, "Content-Type");
                    context.response().headers().add("Access-Control-Allow-Headers", allowHeaders);
                    context.response().headers().add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
                });
            }
        }
        context.next();
    }
}
