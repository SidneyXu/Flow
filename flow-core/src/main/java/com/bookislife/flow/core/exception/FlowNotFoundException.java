package com.bookislife.flow.core.exception;

/**
 * Created by SidneyXu on 2016/05/10.
 */
public class FlowNotFoundException extends FlowException {

    public FlowNotFoundException(String message) {
        super(OBJECT_NOT_FOUND, message);
    }
}
