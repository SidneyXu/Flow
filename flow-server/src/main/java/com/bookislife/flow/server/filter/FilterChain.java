package com.bookislife.flow.server.filter;

import com.bookislife.flow.core.exception.FlowException;
import io.vertx.rxjava.ext.web.RoutingContext;

/**
 * Created by SidneyXu on 2016/06/26.
 */
public interface FilterChain {
    void doFilter(RoutingContext context) throws FlowException;
}
