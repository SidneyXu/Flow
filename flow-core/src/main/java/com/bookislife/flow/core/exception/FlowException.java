package com.bookislife.flow.core.exception;

/**
 * Created by SidneyXu on 2016/04/29.
 */
public class FlowException extends Exception {

    public static final int UNKNOWN_ERROR = -1;
    public static final int OBJECT_NOT_FOUND = 200;

    public final int errorCode;
    public final String errorMessage;

    public FlowException(int errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.errorMessage = message;
    }

    public FlowException(int errorCode, String message) {
        this(errorCode, message, null);
    }

    public FlowException(String message, Throwable cause) {
        this(-1, message, cause);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FlowException{");
        sb.append("errorCode=").append(errorCode);
        sb.append('}');
        return sb.toString();
    }
}
