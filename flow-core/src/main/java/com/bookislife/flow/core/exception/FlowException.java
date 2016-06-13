package com.bookislife.flow.core.exception;

/**
 * Created by SidneyXu on 2016/04/29.
 */
public class FlowException extends Exception {

    public static final int OBJECT_NOT_FOUND = 200;

    public final int code;

    public FlowException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public FlowException(int code, String message) {
        super(message);
        this.code = code;
    }

    public FlowException(String message, Throwable cause) {
        super(message, cause);
        this.code = -1;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FlowException{");
        sb.append("code=").append(code);
        sb.append('}');
        return sb.toString();
    }
}
