package com.bookislife.flow.server.handler;

import com.bookislife.flow.server.handler.impl.StaticResourceHandlerImpl;
import com.google.inject.ImplementedBy;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;

/**
 * Created by SidneyXu on 2016/06/02.
 */
@ImplementedBy(StaticResourceHandlerImpl.class)
public interface StaticResourceHandler extends Handler<RoutingContext> {
}
