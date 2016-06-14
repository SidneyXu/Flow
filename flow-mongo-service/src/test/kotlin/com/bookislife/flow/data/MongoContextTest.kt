package com.bookislife.flow.data

import org.junit.Test

/**
 * Created by SidneyXu on 2016/06/14.
 */
class MongoContextTest {

    @Test
    fun test01() {
        val options = MongoClientOptions.newBuilder()
                .url("127.0.0.1", 27017)
                .create()
        val context = MongoContext()
        val client = context.getClient(options)
        client.listDatabaseNames().forEach {
            println(it)
        }
    }
}