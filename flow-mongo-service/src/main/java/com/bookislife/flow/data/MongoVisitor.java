package com.bookislife.flow.data;

/**
 * Created by SidneyXu on 2016/05/10.
 */
public class MongoVisitor {

    public final MongoClientOptions options;
    public final String dbName;

    public MongoVisitor(MongoClientOptions options, String dbName) {
        this.options = options;
        this.dbName = dbName;
    }
}
