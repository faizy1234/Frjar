package com.example.frjarcustomer.data.local.datastore

import com.google.gson.Gson
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreSerializers @Inject constructor(
    private val gson: Gson
) {

    fun <T> toJson(value: T, type: Type): String = gson.toJson(value, type)

    fun <T> fromJsonOrNull(json: String?, type: Type): T? {
        if (json.isNullOrBlank()) return null
        return runCatching { gson.fromJson<T>(json, type) }.getOrNull()
    }

    fun toJsonReified(value: Any): String = gson.toJson(value)

    fun <T> fromJsonOrNullReified(json: String?, type: Class<T>): T? {
        if (json.isNullOrBlank()) return null
        return runCatching { gson.fromJson(json, type) }.getOrNull()
    }
}
