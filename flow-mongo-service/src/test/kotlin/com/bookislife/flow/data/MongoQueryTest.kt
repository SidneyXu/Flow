package com.bookislife.flow.data

import org.junit.Test
import java.util.*

/**
 * Created by SidneyXu on 2016/06/12.
 */
class MongoQueryTest : BaseTest() {

    @Test
    fun test01() {
        val commentQuery = BaseQuery.from("t_comment")
        val condition = commentQuery.newCondition()
                .eq("author", "Jane")
                .gt("publish_date", Date())
                .link("blog_id", Condition.Link("t_blog", "id"))
                .create()
        commentQuery.condition = condition

        val constraint = commentQuery.newConstraint()
                .limit(100)
                .skip(10)
                .include("title")
                .include("description")
                .sort("id", true)
                .sort("name", false)
                .create()
        commentQuery.constraint = constraint

        val projection = commentQuery.newProjection()
                .select("id")
                .select("name")
                .create()
        commentQuery.projection = projection

        val queryJson = write(commentQuery)
        val mongoQuery = str2obj(queryJson, MongoQuery::class.java)
        println(mongoQuery)

        /*
            {
              "tableName" : "t_comment",
              "condition" : {
                "where" : {
                  "blog_id" : {
                    "$link" : {
                      "refTable" : "t_blog",
                      "refColumn" : "id"
                    }
                  },
                  "author" : {
                    "$eq" : "Jane"
                  },
                  "publish_date" : {
                    "$gt" : 1466639417305
                  }
                }
              },
              "constraint" : {
                "limit" : 100,
                "skip" : 10,
                "includes" : [ "title", "description" ],
                "sort" : "+id,-name"
              },
              "projection" : {
                "selects" : [ "name", "id" ]
              }
            }
         */
    }
}