package com.bookislife.flow.data;

import com.bookislife.flow.core.utils.Pair;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;

import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by SidneyXu on 2016/06/18.
 */
@Singleton
public class MySqlContext extends DBContext {

    private Cache<String, Pair<Connection, DSLContext>> cache;

    public MySqlContext() {
        super();
        cache = CacheBuilder.newBuilder()
                .expireAfterAccess(getExpires(), TimeUnit.MILLISECONDS)
                .removalListener(removalNotification -> {
                    Pair<Connection, DSLContext> pair = (Pair<Connection, DSLContext>) removalNotification.getValue();
                    pair.second.close();
                    try {
                        pair.first.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                })
                .build();
        startCleaner();
    }

    @Override
    protected void clean() {
        cache.cleanUp();
    }

    public DSLContext getContext(MySqlClientOptions options) {
        try {
            return cache.get(options.url, () -> {
                Connection connection = java.sql.DriverManager.getConnection(options.url, options.username, options.password);
                Settings settings = new Settings();
                settings.setExecuteLogging(true);
                settings.setRenderFormatted(true);

                DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL, settings);
                return new Pair<>(connection, dslContext);
            }).second;
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
