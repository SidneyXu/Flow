package com.bookislife.flow.data

import org.junit.Test

/**
 * Created by SidneyXu on 2016/06/14.
 */
class MongoClientOptionsTest {

    @Test
    fun test01() {
        val options = MongoClientOptions.newBuilder()
                .url("10.10.1.10:8080")
                .url("10.10.1.20,8081")
                .urls(listOf("10.10.1.101:10021", "10.10.1.102:10027"))
                .create()

        assert("10.10.1.101:10021,10.10.1.102:10027" == options.connectionUrl)
        assert(options.serverAddress.size == 2)
    }
}