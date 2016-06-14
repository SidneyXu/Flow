package com.bookislife.flow.data

import com.google.inject.AbstractModule
import com.google.inject.Guice
import org.junit.Before
import org.junit.Test
import kotlin.properties.Delegates

/**
 * Created by SidneyXu on 2016/06/14.
 */
class MongoDBStorageTest {

    var stoarge: MongoDBStorage by Delegates.notNull<MongoDBStorage>()
    val databaseName = "test"
    val tableName = "MongoDBStorageTest"

    @Before
    fun setup() {
        val injector = Guice.createInjector(BaseModule())
        stoarge = injector.getInstance(MongoDBStorage::class.java)
    }

    @Test
    fun testInsert() {
        val data = """
        {
            "name":"Peter",
            "age":20,
            "admin":true
        }
        """
        val entity = stoarge.insert(databaseName, tableName, data)
        println(entity)
        assert(entity.id != null)
        assert(entity.createdAt != 0L)
        assert(entity.updatedAt != 0L)
        assert(entity.createdAt == entity.updatedAt)
    }

    @Test
    fun testGet() {
        val data = """
        {
            "name":"Peter",
            "age":20,
            "admin":true
        }
        """
        val entity = stoarge.insert(databaseName, tableName, data)
        val entityAgain = stoarge.findById(databaseName, tableName, entity.id) as MongoEntity

        println(entity)
        println(entityAgain)

        assert(entityAgain.id != null)
        assert(entityAgain.createdAt != 0L)
        assert(entityAgain.updatedAt != 0L)
        assert(entityAgain.createdAt == entity.updatedAt)

        assert(entityAgain.id == entity.id)
        assert("Peter" == entityAgain.document.getString("name"))
        assert(20 == entityAgain.document.getInteger("age"))
        assert(entityAgain.document.getBoolean("admin"))
    }

    class BaseModule : AbstractModule() {

        override fun configure() {
            bind(MongoContext::class.java)
            bind(MongoDao::class.java)
            bind(MongoDBStorage::class.java)
        }

    }
}