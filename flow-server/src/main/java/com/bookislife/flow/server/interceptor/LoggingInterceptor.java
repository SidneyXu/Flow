package com.bookislife.flow.server.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Created by SidneyXu on 2016/05/05.
 */
public class LoggingInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        return methodInvocation.proceed();
    }
}
