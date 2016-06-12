package com.bookislife.flow.data

import org.junit.Test

/**
 * Created by SidneyXu on 2016/06/12.
 */
class ConstraintTest : BaseTest() {

    @Test
    fun test01() {
        val commentQuery = BaseQuery.from("t_comment")
        val constraint = commentQuery.newConstraint()
                .limit(100)
                .skip(10)
                .include("title")
                .include("description")
                .sort("id", true)
                .sort("name", false)
                .create()
        write(constraint)

        /*
            {
              "limit" : 100,
              "skip" : 10,
              "includes" : [ "title", "description" ],
              "sort" : "+id,-name"
            }
         */
    }
}