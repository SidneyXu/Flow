package com.bookislife.flow.data

import org.junit.Test
import java.util.*

/**
 * Created by SidneyXu on 2016/06/12.
 */
class ConditionTest : BaseTest() {

    @Test
    fun test01() {
        val condition = Condition.newBuilder()
                .addCondition("\$eq", "x", 1)
                .addCondition("\$in", "color", Arrays.asList("yellow", "blue"))
                .addCondition("\$exists", "name", true)
                .create()
        write(condition)
    }

    @Test
    fun test02() {
        val condition = Condition.newBuilder()
                .eq("author", "Jane")
                .gt("publish_date", Date())
                .link("blog_id", Condition.Link("t_blog", "id"))
                .create()

        write(condition)

        /*
            {
              "where" : {
                "blog_id" : {
                  "$like" : {
                    "refTable" : "t_blog",
                    "refColumn" : "id"
                  }
                },
                "author" : {
                  "$eq" : "Jane"
                },
                "publish_date" : {
                  "$gt" : 1465714390132
                }
              }
            }
         */
    }

    @Test
    fun testLike() {
        val condition = Condition.newBuilder()
                .like("name","Jack.*", "i")
                .create()
        write(condition)
    }

    @Test
    fun testLink() {
        val condition = Condition.newBuilder()
                .addCondition("\$link", "id", Condition.Link("t_blog", "id"))
                .create()
        write(condition)

        /*
            {
                id: {
                  "$link":"t_blog.id"
                }
            }
         */
    }

    @Test
    fun testOr() {
        val condition1 = Condition.newBuilder()
                .lt("age", 20)
                .create()
        val condition2 = Condition.newBuilder()
                .eq("admin", true)
                .create()
        val cond = Condition.newBuilder()
                .or(condition1, condition2)
                .eq("name","Jack")
                .ne("age",10)
                .create()
        write(cond)
        /*
            {
              "where" : {
                "$or" : [ {
                  "age" : {
                    "$lt" : 20
                  }
                }, {
                  "admin" : {
                    "$eq" : true
                  }
                } ],
                "name" : {
                  "$eq" : "Jack"
                },
                "age" : {
                  "$ne" : 10
                }
              }
            }
         */
    }

}