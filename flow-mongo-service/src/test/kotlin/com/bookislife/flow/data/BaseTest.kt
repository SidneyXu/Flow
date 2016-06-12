package com.bookislife.flow.data

import com.bookislife.flow.data.utils.JacksonDecoder
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import org.junit.Before

/**
 * Created by SidneyXu on 2016/06/12.
 */
abstract class BaseTest {

    private var writer: ObjectWriter? = null

    @Before
    fun setup() {
        val objectMapper = ObjectMapper()
        writer = objectMapper.writerWithDefaultPrettyPrinter()
    }

    fun write(obj: Any): String {
        val result = writer!!.writeValueAsString(obj)
        println(result)
        return result
    }

    fun str2obj(json: String, clazz: Class<*>): Any {
        return JacksonDecoder.decode(json, clazz)
    }
}