package com.bookislife.flow.sdk;

/**
 * Created by SidneyXu on 2016/06/25.
 */
public class ConsoleLogService extends LogService {

    @Override
    protected void internalLog(String tag, String message) {
        System.out.println(tag + " : " + message);
    }
}
