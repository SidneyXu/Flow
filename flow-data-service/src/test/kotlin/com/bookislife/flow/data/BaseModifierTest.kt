package com.bookislife.flow.data

import org.junit.Test

/**
 * Created by SidneyXu on 2016/06/12.
 */
class BaseModifierTest : BaseTest() {

    @Test
    fun testModifier() {
        val modifier = BaseModifier.newBuilder()
                .addUpdater(BaseModifier.INC, "name", 100)
                .create()
        write(modifier)

        /*
            {
              "modifiers" : {
                "$inc" : {
                  "name" : 100
                }
              }
            }
         */
    }
}