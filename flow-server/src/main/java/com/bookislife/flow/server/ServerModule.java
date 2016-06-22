package com.bookislife.flow.server;

import com.bookislife.flow.data.module.DataServiceModule;
import com.google.inject.AbstractModule;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.Vertx;

/**
 * Created by SidneyXu on 2016/05/03.
 */
public class ServerModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new DataServiceModule());
    }
}