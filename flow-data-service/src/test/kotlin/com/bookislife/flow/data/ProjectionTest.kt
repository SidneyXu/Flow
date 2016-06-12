package com.bookislife.flow.data

import com.fasterxml.jackson.databind.ObjectWriter
import org.junit.Test

/**
 * Created by SidneyXu on 2016/06/12.
 */
class ProjectionTest : BaseTest() {

    private var writer: ObjectWriter? = null

    @Test
    fun test01() {
        val projection = Projection.newBuilder()
                .select("name")
                .select("age")
                .select("friend.name")
                .create()
        write(projection)

        /*
            {
              "selects" : [ "friend.name", "name", "age" ]
            }
         */
    }
}