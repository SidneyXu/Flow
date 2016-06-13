package com.bookislife.flow.sdk.web;

import com.bookislife.flow.core.exception.FlowException;

/**
 * Created by SidneyXu on 2016/06/13.
 */
public abstract class RequestClient<REQUEST, RESPONSE> {

    public abstract FResponse execute(FRequest fRequest) throws FlowException;

    protected abstract REQUEST getRequest(FRequest fRequest) throws FlowException;

    protected abstract FResponse getResponse(RESPONSE response) throws FlowException;
}
