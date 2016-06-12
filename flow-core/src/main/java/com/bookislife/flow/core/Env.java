package com.bookislife.flow.core;

/**
 * Created by SidneyXu on 2016/05/04.
 */
public interface Env {

    public interface Config {
        String MONGO_CONFIG_FILE_NAME = "mongo-config.json";
        String MONGO_CONFIG_PROP_NAME = "mongo.config";
        String DB_DIALECT = "db.dialect";
        String DB_CLEANER_INTERVAL = "db.cleaner.interval";
        String DB_CONNECTION_EXPIRES = "db.connection.expires";
    }

    public interface File {
        String FLOW_DB_CONFIG_FILE_NAME = "flow.db.properties";
        String FLOW_SERVER_CONFIG_FILE_NAME = "flow.properties";
    }

    public interface Header {
        String RESPONSE_TIME = "x-flow-response-time";
        String APPLICATION_ID = "x-flow-application-id";
        String VERSION = "x-flow-version";
        String SESSION_TOKEN = "x-flow-session-token";
    }

    public interface Default {
        long dbCleanerInterval = 600_000;
        long dbConnectionExpires = 18_000_000;
    }

}
