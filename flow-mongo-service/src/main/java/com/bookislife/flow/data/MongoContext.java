package com.bookislife.flow.data;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.mongodb.MongoClient;

import javax.inject.Singleton;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by SidneyXu on 2016/05/03.
 */
@Singleton
public class MongoContext extends DBContext {

    private Cache<String, MongoClient> cache;

    public MongoContext() {
        super();
        cache = CacheBuilder.newBuilder()
                .expireAfterAccess(getExpires(), TimeUnit.MILLISECONDS)
                .removalListener(removalNotification -> {
                    MongoClient client = (MongoClient) removalNotification.getValue();
                    client.close();
                })
                .build();
        startCleaner();
    }

    @Override
    protected void clean() {
        cache.cleanUp();
    }

    public MongoClient getClient(MongoClientOptions options) {
        try {
            return cache.get(options.getConnectionUrl(), () -> new MongoClient(options.getServerAddress()));
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
