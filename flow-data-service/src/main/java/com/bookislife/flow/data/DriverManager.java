package com.bookislife.flow.data;

import com.google.inject.Binder;

/**
 * Created by SidneyXu on 2016/05/24.
 */
public class DriverManager {

    private static Driver driver;
    private static Binder binder;

    public static void register(Driver driver) {
        DriverManager.driver = driver;
        driver.configure(binder);
    }

    public static Driver getDriver() {
        return driver;
    }

    public static void setBinder(Binder binder) {
        DriverManager.binder = binder;
    }
}
