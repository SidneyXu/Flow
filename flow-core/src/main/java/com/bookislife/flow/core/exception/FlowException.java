package com.bookislife.flow.core.exception;

/**
 * Created by SidneyXu on 2016/04/29.
 */
public class FlowException extends Exception {

    public static final int UNKNOWN_ERROR = -1;
    public static final int OBJECT_NOT_FOUND = 200;
    public static final int ILLEGAL_ARGUMENTS = 201;

    public final int errorCode;
    public final String errorMessage;
    public final int statusCode;

    public FlowException(int errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.errorMessage = message;
        this.statusCode = 0;
    }

    public FlowException(int errorCode, String message) {
        this(errorCode, message, null);
    }

    public FlowException(String message, Throwable cause) {
        this(-1, message, cause);
    }

    public FlowException(String message, int statusCode) {
        this.errorCode = -1;
        this.errorMessage = message;
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FlowException{");
        sb.append("errorCode=").append(errorCode);
        sb.append(", errorMessage='").append(errorMessage).append('\'');
        sb.append(", statusCode=").append(statusCode);
        sb.append('}');
        return sb.toString();
    }
}
