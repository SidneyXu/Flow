package com.bookislife.flow.sdk;

/**
 * Created by SidneyXu on 2016/06/25.
 */
public abstract class LogService {

    private int currentLevel = LOG_LEVEL_NONE;

    public String tag = "Flow";

    public static final int LOG_LEVEL_VERBOSE = 10;
    public static final int LOG_LEVEL_INFO = 20;
    public static final int LOG_LEVEL_WARNING = 30;
    public static final int LOG_LEVEL_DEBUG = 40;
    public static final int LOG_LEVEL_ERROR = 50;
    public static final int LOG_LEVEL_NONE = 99;

    public LogService() {
    }

    public LogService(String tag) {
        this.tag = tag;
    }

    public void d(String tag, String message) {
        log(LOG_LEVEL_DEBUG, tag, message);
    }

    public void d(String message) {
        d(tag, message);
    }

    public void e(String tag, Throwable throwable) {
        log(LOG_LEVEL_ERROR, tag, throwable.getMessage());
    }

    public void e(Throwable throwable) {
        e(tag, throwable);
    }

    private void log(int level, String tag, String message) {
        if (currentLevel < level) {
            return;
        }
        internalLog(tag, message);
    }

    protected abstract void internalLog(String tag, String message);
}
