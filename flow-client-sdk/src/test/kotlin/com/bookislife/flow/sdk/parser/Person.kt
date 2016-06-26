package com.bookislife.flow.sdk.parser

import com.bookislife.flow.core.domain.BaseEntity

/**
 * Created by SidneyXu on 2016/06/25.
 */
class Person : BaseEntity() {

    init {
        data = mutableMapOf()
    }

    fun setName(value: String) {
        data.put("name", value)
    }

    fun getName(): String = data.get("name") as String

    fun setAge(value: Int) {
        data.put("age", value)
    }

    fun getAge(): Int = data.get("age") as Int

}