package com.bookislife.flow.sdk.parser

import com.bookislife.flow.core.domain.BaseEntity
import com.bookislife.flow.sdk.FlowApi
import com.bookislife.flow.sdk.ObjectService
import org.junit.Before
import org.junit.Test
import kotlin.properties.Delegates

/**
 * Created by SidneyXu on 2016/06/24.
 */
class ApiTest {

    var api: FlowApi by Delegates.notNull<FlowApi>()
    var objectService: ObjectService by Delegates.notNull<ObjectService>()

    @Before
    fun setup() {
        api = FlowApi.Builder()
                .applicationId("123")
                .targetServer("http://localhost:10087")
                .build()
        objectService = api.objectService
    }

    @Test
    fun testSave() {
        val type = "person"
        val entity = BaseEntity()
        entity.data = mapOf(
                Pair("name", "Jack"),
                Pair("age", 20)
        )
        objectService.save(type, entity)
        assert(entity.id != null)
        assert(entity.createdAt > 0)
        assert(entity.updatedAt > 0)
    }

    @Test
    fun testGet() {
        val type = "person"
        val entity = objectService.get(type, "576cf4fba0a9b4a0799a4a8f")
        println(entity)
    }
}