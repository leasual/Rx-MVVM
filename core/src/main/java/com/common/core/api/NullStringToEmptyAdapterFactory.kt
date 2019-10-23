package com.common.core.api

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken

/**
 * Created by james.li on 2018/8/21.
 */
@Suppress("UNCHECKED_CAST")
class NullStringToEmptyAdapterFactory : TypeAdapterFactory {
    override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {

        val rawType = type.rawType as Class<T>
        if (rawType != String::class.java) {
            return null
        }
        return StringAdapter() as TypeAdapter<T>
    }
}
