package com.bookislife.flow.data;

import com.google.inject.Binder;

/**
 * Created by SidneyXu on 2016/05/24.
 */
public final class MongoDriver implements Driver {

    static {
        DriverManager.register(new MongoDriver());
    }

    private MongoDriver() {
    }

    @Override
    public void configure(Binder binder) {
        binder.bind(MongoContext.class);
        binder.bind(MongoDao.class);
        binder.bind(DBStorage.class).to(MongoDBStorage.class);
        binder.bind(DBSchemaService.class).to(MongoSchemaService.class);
    }
}
