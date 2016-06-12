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
                .addCondition("\$like", "name", "/Jack.*/i")
                .addCondition("\$exists", "name", true)
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

}