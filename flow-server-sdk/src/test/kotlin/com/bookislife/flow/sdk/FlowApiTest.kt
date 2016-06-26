package com.bookislife.flow.sdk

import com.bookislife.flow.core.domain.BaseEntity
import com.bookislife.flow.data.MongoContext
import com.bookislife.flow.data.MongoEntity
import org.junit.Before
import org.junit.Test
import kotlin.properties.Delegates

/**
 * Created by SidneyXu on 2016/06/26.
 */
class FlowApiTest {

    var service by Delegates.notNull<ObjectService>()

    @Before
    fun setup() {
        val api = FlowApi.Builder()
                .applicationId("server")
                .build()
        service = api.objectService
    }

    @Test
    fun testSave() {
        val type = "person"
        val entity = MongoEntity(mutableMapOf())
        entity.data = mapOf(
                Pair("name", "Jack"),
                Pair("age", 22)
        )
        service.save(type, entity)
        assert(entity.id != null)
        assert(entity.createdAt > 0)
        assert(entity.updatedAt > 0)
    }
}