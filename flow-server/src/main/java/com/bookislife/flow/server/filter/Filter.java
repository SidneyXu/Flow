package com.bookislife.flow.server.filter;

import com.bookislife.flow.core.exception.FlowException;
import io.vertx.rxjava.ext.web.RoutingContext;

/**
 * Created by SidneyXu on 2016/06/11.
 */
public interface Filter {

    void init() throws FlowException;

    void doFilter(RoutingContext context, FilterChain chain) throws FlowException;

}
