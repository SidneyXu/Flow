package com.bookislife.flow.sdk.parser

import com.bookislife.flow.core.exception.FlowException
import org.junit.Test

/**
 * Created by SidneyXu on 2016/06/14.
 */
class JSONDecoderTest {

    @Test
    fun testExceptionWithDefaultDecoder() {
        val json = """
        {
            "errorCode": 100,
            "errorMessage": "foobar"
        }
        """
        val e = JSONParser.decode(json, FlowException::class.java)
        println(e)
        println("${e.errorCode} -> ${e.errorMessage}")
    }

    @Test
    fun testExceptionWithGsonDecoder() {
        val json = """
        {
            "errorCode": 100,
            "errorMessage": "foobar"
        }
        """
        JSONParser.setParser(GsonParser())
        val e = JSONParser.decode(json, FlowException::class.java)
        println(e)
        println("${e.errorCode} -> ${e.errorMessage}")
    }
}