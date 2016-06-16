package com.bookislife.flow.data

import com.google.inject.AbstractModule
import com.google.inject.Guice
import org.junit.After
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

    @After
    fun tearDown() {
        stoarge.deleteAll(databaseName, tableName, null)
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

    @Test
    fun findAll() {
        perpareQueryData()
        val query = """
            {
               "tableName" : "$tableName",
               "condition": {
                   "where" : {
                       "age":{
                       "${'$'}eq":20
                       }
                   }
               }
            }
        """
        val entities = stoarge.findAll(databaseName, tableName, query)
        println(entities.size)
        entities.forEach { println(it) }

        assert(entities.size == 2)
        entities.forEach {
            val entity = it as MongoEntity
            assert(entity.getInt("age") == 20)

            assert(entity.id != null)
            assert(entity.createdAt != 0L)
            assert(entity.updatedAt != 0L)
            assert(entity.createdAt == entity.updatedAt)
        }
    }

    @Test
    fun delete() {
        val data = perpareQueryData()
        val n = stoarge.delete(databaseName, tableName, data[1].id)
        assert(n == 1)

        val entities = stoarge.findAll(databaseName, tableName, null)
        assert(entities.size == 5)
        entities.forEach {
            println(it)

            val entity = it as MongoEntity
            assert(entity.getString("name") != "Jane")
            assert(entity.getInt("age") != 18)
        }
    }

    @Test
    fun deleteAll() {
        val n = stoarge.deleteAll(databaseName, tableName, null)
        println(n)
    }

    @Test
    fun count() {
        val list = perpareQueryData()
        val n = stoarge.count(databaseName, tableName, null)
        assert(list.size == n.toInt())
    }

    @Test
    fun countByQuery() {
        perpareQueryData()
        val query = """
            {
               "tableName" : "$tableName",
               "condition": {
                   "where" : {
                       "age":{
                       "${'$'}eq":20
                       }
                   }
               }
            }
        """
        val n = stoarge.count(databaseName, tableName, query)
        assert(2 == n.toInt())
    }

    //TODO
    fun update() {


    }

    @Test
    fun testEq() {
        val list = perpareQueryData()
        val expect = list.map {
            if (it.getInt("age") == 20) {
                1
            } else {
                0
            }
        }.sum()
        val query = """
            {
               "tableName" : "$tableName",
               "condition": {
                   "where" : {
                       "age":{
                       "${'$'}eq":20
                       }
                   }
               }
            }
        """
        val result = stoarge.findAll(databaseName, tableName, query)
        assert(expect == result.size)
    }

    @Test
    fun testNotEq() {
        val list = perpareQueryData()
        val expect = list.map {
            if (it.getInt("age") == 20) {
                0
            } else {
                1
            }
        }.sum()
        val query = """
            {
               "tableName" : "$tableName",
               "condition": {
                   "where" : {
                       "age":{
                       "${'$'}ne":20
                       }
                   }
               }
            }
        """
        val result = stoarge.findAll(databaseName, tableName, query)
        println(result.size)
        assert(expect == result.size)
    }

    @Test
    fun testGreater() {
        val list = perpareQueryData()
        val expect = list.map {
            if (it.getInt("age") > 20) {
                1
            } else {
                0
            }
        }.sum()
        val query = """
            {
               "tableName" : "$tableName",
               "condition": {
                   "where" : {
                       "age":{
                       "${'$'}gt":20
                       }
                   }
               }
            }
        """
        val result = stoarge.findAll(databaseName, tableName, query)
        println(result.size)
        assert(expect == result.size)
    }

    @Test
    fun testGreaterThanOrEqualTo() {
        val list = perpareQueryData()
        val expect = list.map {
            if (it.getInt("age") >= 20) {
                1
            } else {
                0
            }
        }.sum()
        val query = """
            {
               "tableName" : "$tableName",
               "condition": {
                   "where" : {
                       "age":{
                       "${'$'}gte":20
                       }
                   }
               }
            }
        """
        val result = stoarge.findAll(databaseName, tableName, query)
        println(result.size)
        assert(expect == result.size)
    }

    @Test
    fun testSmaller() {
        val list = perpareQueryData()
        val expect = list.map {
            if (it.getInt("age") < 20) {
                1
            } else {
                0
            }
        }.sum()
        val query = """
            {
               "tableName" : "$tableName",
               "condition": {
                   "where" : {
                       "age":{
                       "${'$'}lt":20
                       }
                   }
               }
            }
        """
        val result = stoarge.findAll(databaseName, tableName, query)
        println(result.size)
        assert(expect == result.size)
    }

    @Test
    fun testSmallerThanOrEqualTo() {
        val list = perpareQueryData()
        val expect = list.map {
            if (it.getInt("age") <= 20) {
                1
            } else {
                0
            }
        }.sum()
        val query = """
            {
               "tableName" : "$tableName",
               "condition": {
                   "where" : {
                       "age":{
                       "${'$'}lte":20
                       }
                   }
               }
            }
        """
        val result = stoarge.findAll(databaseName, tableName, query)
        println(result.size)
        assert(expect == result.size)
    }

    @Test
    fun testIn() {
        val list = perpareQueryData()
        val expect = list.map {
            if (it.getInt("age") == 20 || it.getInt("age") == 18) {
                1
            } else {
                0
            }
        }.sum()
        val query = """
            {
               "tableName" : "$tableName",
               "condition": {
                   "where" : {
                       "age":{
                       "${'$'}in":[20, 18]
                       }
                   }
               }
            }
        """
        val result = stoarge.findAll(databaseName, tableName, query)
        println(result.size)
        assert(expect == result.size)
    }

    @Test
    fun testNotIn() {
        val list = perpareQueryData()
        val expect = list.map {
            if (it.getInt("age") != 20 && it.getInt("age") != 18) {
                1
            } else {
                0
            }
        }.sum()
        val query = """
            {
               "tableName" : "$tableName",
               "condition": {
                   "where" : {
                       "age":{
                       "${'$'}nin":[20, 18]
                       }
                   }
               }
            }
        """
        val result = stoarge.findAll(databaseName, tableName, query)
        println(result.size)
        assert(expect == result.size)
    }

    @Test
    fun testExist() {
        val list = perpareQueryData()
        val expect = list.map {
            if (it.getBoolean("admin")!=null) {
                1
            } else {
                0
            }
        }.sum()
        val query = """
            {
               "tableName" : "$tableName",
               "condition": {
                   "where" : {
                       "admin":{
                       "${'$'}exists":true
                       }
                   }
               }
            }
        """
        val result = stoarge.findAll(databaseName, tableName, query)
        println(result.size)
        assert(expect == result.size)
    }

    @Test
    fun testNotExist() {
        val list = perpareQueryData()
        val expect = list.map {
            if (it.getBoolean("admin")==null) {
                1
            } else {
                0
            }
        }.sum()
        val query = """
            {
               "tableName" : "$tableName",
               "condition": {
                   "where" : {
                       "admin":{
                       "${'$'}exists":false
                       }
                   }
               }
            }
        """
        val result = stoarge.findAll(databaseName, tableName, query)
        println(result.size)
        assert(expect == result.size)
    }

    fun testLink() {

    }

    fun testComplex() {

    }

    fun errorGrammar() {

    }

    fun unknowOperator() {

    }

    private fun perpareQueryData(): List<MongoEntity> {
        val list = listOf(
                Triple("Peter", 20, true),
                Triple("Jane", 18, true),
                Triple("Terry", 20, false),
                Triple("Anna", 22, false),
                Triple("Ken", 22, true),
                Triple("Ryu", 19, false),
                Triple("Beer", 10, null)
        )
        return list.map {
            val data = """
        {
            "name":"${it.first}",
            "age": ${it.second}
            ${if (it.third != null) {
                ",\"admin\":" + it.third
            } else {
                ""
            }}
        }
        """
            stoarge.insert(databaseName, tableName, data)
        }.map {
            it as MongoEntity
        }
    }

    class BaseModule : AbstractModule() {

        override fun configure() {
            bind(MongoContext::class.java)
            bind(MongoDao::class.java)
            bind(MongoDBStorage::class.java)
        }

    }
}