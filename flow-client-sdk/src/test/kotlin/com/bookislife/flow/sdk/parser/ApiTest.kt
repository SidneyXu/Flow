package com.bookislife.flow.sdk.parser

import com.bookislife.flow.core.domain.BaseEntity
import com.bookislife.flow.data.BaseModifier
import com.bookislife.flow.data.BaseQuery
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
                .applicationId("test")
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
                Pair("age", 22)
        )
        objectService.save(type, entity)
        assert(entity.id != null)
        assert(entity.createdAt > 0)
        assert(entity.updatedAt > 0)
    }

    @Test
    fun testSave2() {
        val person = Person().apply {
            setName("Jane")
            setAge(16)
        }
        objectService.save("person", person)
    }

    @Test
    fun testDelete() {
        val n = objectService.delete("person", "576e92c3b740de2561d651bb")
        println(n)
    }

    @Test
    fun testGet() {
        val type = "person"
        val entity: BaseEntity = objectService.get(type, "576e92c3b740de2561d651bb")
        println(entity)
    }

    @Test
    fun testGet2() {
        val person: Person = objectService.get(Person::class.java, "person", "576efb89b740de2d2d3e1278")
        println(person)
        println(person.getName())
        println(person.getAge())
    }

    @Test
    fun testUpdate() {
        val modifier = BaseModifier.newBuilder()
                .set("name", "abc")
                .create()
        val n = objectService.update("person", "576efb89b740de2d2d3e1278", modifier)
        println(n)
    }

    @Test
    fun testFind() {
        val query = BaseQuery("person")
        val cond = query.newCondition()
        cond.gte("age", 22)
        query.condition = cond.create()
        val persons = objectService.find(query)
        println(persons.size)
        persons.forEach {
            println(it)
        }
    }
}