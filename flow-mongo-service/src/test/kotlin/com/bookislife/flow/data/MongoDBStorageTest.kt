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
    @Test
    fun update() {
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
        val modifier = """
        {
            "modifiers":{
                "${'$'}set":{
                    "name":"Foobar"
                }
            }
        }
        """
        val n = stoarge.update(databaseName, tableName, query, modifier)
        println(n)

        val result = stoarge.findAll(databaseName, tableName, query)
        result.forEach {
            println(it)
        }
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
            if (it.getBoolean("admin") != null) {
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
            if (it.getBoolean("admin") == null) {
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

    @Test
    fun testOr() {
        val list = perpareQueryData()
        val expect = list.map {
            if (it.getInt("age") == 22 || it.getBoolean("admin") == false) {
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
                       "${'$'}or":[{
                            "age": {
                                "${'$'}eq":22
                            }},{
                            "admin": {
                                "${'$'}eq":false
                            }
                        }]
                   }
               }
            }
        """
        val result = stoarge.findAll(databaseName, tableName, query)
        println(result.size)
        assert(expect == result.size)
    }

    @Test
    fun testLike() {
        val list = perpareQueryData()
        val expect = list.map {
            if (it.getString("name").startsWith("T")) {
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
                       "name":{
                            "${'$'}regex":"/^T/"
                        }
                   }
               }
            }
        """
        val result = stoarge.findAll(databaseName, tableName, query)
        println(result.size)
        result.forEach { println(it) }
        assert(expect == result.size)
    }

    @Test
    fun testLikeWithFlag() {
        val list = perpareQueryData()
        val expect = list.map {
            if (it.getString("name").startsWith("T", true)) {
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
                       "name":{
                            "${'$'}regex":"/^T/i"
                        }
                   }
               }
            }
        """
        val result = stoarge.findAll(databaseName, tableName, query)
        println(result.size)
        result.forEach { println(it) }
        assert(expect == result.size)
    }

    @Test
    fun updateLink() {
        val blogDoc = """
        {
            "name":"Blog One"
        }
        """
        val blog = stoarge.insert(databaseName, "t_blog", blogDoc)

        val commentDoc = """
        {
            "name":"Comment One on Blog One"
        }
        """
        val comment = stoarge.insert(databaseName, "t_comment", commentDoc)

        val query = """
            {
               "tableName" : "t_comment",
               "condition": {
                   "where" : {
                       "id":{
                            "${'$'}eq":"${comment.id}"
                        }
                   }
               }
            }
        """
        val modifies = """
        {
            "modifiers":{
                "${'$'}set":{
                    "blog_id":{
                        "${'$'}ref":"t_blog",
                        "${'$'}id":"${blog.id}"
                    }
                }
            }
        }
        """
        val n = stoarge.update(databaseName, "t_comment", query, modifies)
        println(n)
    }

    @Test
    fun testLink() {
        val query = """
            {
               "tableName" : "t_comment",
               "condition": {
                   "where" : {
                       "blog_id":{
                            "${'$'}ref":"t_blog",
                            "${'$'}id":"57649c99b740de0b0773ec64"
                        }
                   }
               }
            }
        """
        val comment = stoarge.findAll(databaseName, "t_comment", query)
        println(comment)
    }

    fun testComplex() {

    }

    @Test
    fun testSkip() {
        val n = 2
        val excludes = perpareQueryData()
                .sortedBy { it.getInt("age") }
                .take(n)
        val query = """
            {
               "tableName" : "$tableName",
               "condition": {
                   "where" : {
                   }
               },
               "constraint": {
                    "sort": "+age",
                    "skip":$n
               }
            }
        """
        val result = stoarge.findAll(databaseName, tableName, query)
        result.forEach {
            println(it)
        }
        val unexcepted = result.filter {
            excludes.map {
                it.id
            }.contains(it.id)
        }
        assert(unexcepted.size == 0)
    }

    @Test
    fun testLimit() {
        perpareQueryData()
        val n = 3
        val query = """
            {
               "tableName" : "$tableName",
               "condition": {
                   "where" : {
                   }
               },
               "constraint": {
                    "limit": $n
               }
            }
        """
        val result = stoarge.findAll(databaseName, tableName, query)
        assert(n == result.size)
    }

    @Test
    fun testSelect() {
        perpareQueryData()
        val query = """
            {
               "tableName" : "$tableName",
               "condition": {
                   "where" : {
                   }
               },
               "projection": {
                    "selects": ["name","createdAt"]
               }
            }
        """
        val result = stoarge.findAll(databaseName, tableName, query)
        println(result.size)
        result.forEach { println(it) }
    }

    @Test
    fun sort() {
        perpareQueryData()
        val query = """
            {
               "tableName" : "$tableName",
               "condition": {
                   "where" : {
                   }
               },
               "constraint": {
                    "sort": "-age,+name"
               }
            }
        """
        val result = stoarge.findAll(databaseName, tableName, query)
        println(result.size)
        result.forEach { println(it) }
    }

    @Test
    fun errorGrammar() {
        perpareQueryData()
        val query = """
            {
               "tableName" : "$tableName",
               "condition": {
                   "where" : {
                       "a":/a/
                   }
               }
            }
        """
        stoarge.findAll(databaseName, tableName, query)
    }

    @Test
    fun unknowOperator() {
        perpareQueryData()
        val query = """
            {
               "tableName" : "$tableName",
               "condition": {
                   "where" : {
                       "age":{
                       "${'$'}all":[20]
                       }
                   }
               }
            }
        """
        stoarge.findAll(databaseName, tableName, query)
    }

    private fun perpareQueryData(): List<MongoEntity> {
        val list = listOf(
                Triple("Peter", 20, true),
                Triple("Jane", 18, true),
                Triple("Terry", 20, false),
                Triple("tina", 22, false),
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

    private fun perpareLinkData(): Pair<List<MongoEntity>, List<MongoEntity>> {
        val blogList = listOf(
                "One",
                "Two",
                "Three",
                "Four",
                "Five"
        )
        val blogEntities = blogList.map {
            val data = """
        {
            "name":$it
        }
        """
            stoarge.insert(databaseName, "t_blog", data)
        }.map {
            it as MongoEntity
        }

        val commentList = listOf(
                "One Blog Comment 1",
                "One Blog Comment 2",
                "Two Comment",
                "Three Comment"
        )
        val commentEntities = commentList.map {
            val data = """
        {
            "name":$it
        }
        """
            stoarge.insert(databaseName, "t_blog", data)
        }.map {
            it as MongoEntity
        }

        return Pair(blogEntities, commentEntities)
    }

    class BaseModule : AbstractModule() {

        override fun configure() {
            bind(MongoContext::class.java)
            bind(MongoDao::class.java)
            bind(MongoDBStorage::class.java)
        }

    }
}