package com.bookislife.flow.server;

import com.bookislife.flow.data.module.DataServiceModule;
import com.google.inject.AbstractModule;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.Vertx;

/**
 * Created by SidneyXu on 2016/05/03.
 */
public class ServerModule extends AbstractModule {

    private final Vertx vertx;
    private final JsonObject config;

    public ServerModule(Vertx vertx, JsonObject config) {
        this.vertx = vertx;
        this.config = config;
    }

    @Override
    protected void configure() {
//        MongoClientOptions options = MongoClientOptions.newBuilder()
//                .url("localhost")
//                .create();
//        bind(MongoClientOptions.class).to(options);
//        bind(MongoDao.class);

        install(new DataServiceModule());
//        bind(DBStorage.class).to(MongoDBStorage.class);

    }
}