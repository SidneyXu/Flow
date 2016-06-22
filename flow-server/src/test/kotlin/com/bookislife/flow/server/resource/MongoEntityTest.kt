package com.bookislife.flow.data

import com.bookislife.flow.core.domain.BaseEntity
import com.bookislife.flow.data.utils.JacksonDecoder
import com.bookislife.flow.server.utils.JacksonJsonBuilder
import org.junit.Test

/**
 * Created by SidneyXu on 2016/06/23.
 */
class MongoEntityTest {

    @Test
    fun test01() {
        val data = """
        {
            "y":1
        }
        """
        val document = JacksonDecoder.decode(data, MongoEntity::class.java)
        println(document)
    }

    @Test
    fun test02() {
        val entity = MongoEntity(mapOf(
                Pair("name", "Peter"),
                Pair("age", 10)
        ))
        //TODO
        val result = JacksonJsonBuilder.create().put(BaseEntity.FIELD_DATA, entity.getData()).build()
        println(result)
    }
}