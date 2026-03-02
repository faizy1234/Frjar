package com.example.frjarcustomer.data.local.datastore

import kotlinx.coroutines.flow.Flow

/**
 *  DataStore API.
 * Single instance, type-safe primitives + generic objects, Flow-based reads, batched writes.
 */
interface AppDataStore {

    // ---------- String ----------
    suspend fun getString(key: String): DataStoreResult<String?>
    fun getStringFlow(key: String): Flow<String?>
    suspend fun putString(key: String, value: String?): DataStoreResult<Unit>

    // ---------- Int ----------
    suspend fun getInt(key: String): DataStoreResult<Int?>
    fun getIntFlow(key: String): Flow<Int?>
    suspend fun putInt(key: String, value: Int?): DataStoreResult<Unit>

    // ---------- Long ----------
    suspend fun getLong(key: String): DataStoreResult<Long?>
    fun getLongFlow(key: String): Flow<Long?>
    suspend fun putLong(key: String, value: Long?): DataStoreResult<Unit>

    // ---------- Boolean ----------
    suspend fun getBoolean(key: String): DataStoreResult<Boolean?>
    fun getBooleanFlow(key: String): Flow<Boolean?>
    suspend fun putBoolean(key: String, value: Boolean?): DataStoreResult<Unit>

    // ---------- Float ----------
    suspend fun getFloat(key: String): DataStoreResult<Float?>
    fun getFloatFlow(key: String): Flow<Float?>
    suspend fun putFloat(key: String, value: Float?): DataStoreResult<Unit>

    // ---------- StringSet ----------
    suspend fun getStringSet(key: String): DataStoreResult<Set<String>?>
    fun getStringSetFlow(key: String): Flow<Set<String>?>
    suspend fun putStringSet(key: String, value: Set<String>?): DataStoreResult<Unit>

    // ---------- Generic object (Gson) ----------
    suspend fun <T> getObject(key: String, type: java.lang.reflect.Type): DataStoreResult<T?>
    fun <T> getObjectFlow(key: String, type: java.lang.reflect.Type): Flow<T?>
    suspend fun <T> putObject(key: String, value: T?, type: java.lang.reflect.Type): DataStoreResult<Unit>

    // ---------- Batch & lifecycle ----------
    suspend fun remove(key: String): DataStoreResult<Unit>
    suspend fun clear(): DataStoreResult<Unit>
    suspend fun edit(block: suspend DataStoreEditor.() -> Unit): DataStoreResult<Unit>
}

interface DataStoreEditor {
    fun setString(key: String, value: String?)
    fun setInt(key: String, value: Int?)
    fun setLong(key: String, value: Long?)
    fun setBoolean(key: String, value: Boolean?)
    fun setFloat(key: String, value: Float?)
    fun setStringSet(key: String, value: Set<String>?)
    fun <T> setObject(key: String, value: T?, type: java.lang.reflect.Type)
    fun remove(key: String)
}

// ---------- Reified extensions (cannot be inline on interface) ----------

suspend inline fun <reified T> AppDataStore.getObject(key: String): DataStoreResult<T?> =
    getObject(key, object : com.google.gson.reflect.TypeToken<T>() {}.type)

inline fun <reified T> AppDataStore.getObjectFlow(key: String): Flow<T?> =
    getObjectFlow(key, object : com.google.gson.reflect.TypeToken<T>() {}.type)

suspend inline fun <reified T> AppDataStore.putObject(key: String, value: T?): DataStoreResult<Unit> =
    putObject(key, value, object : com.google.gson.reflect.TypeToken<T>() {}.type)

inline fun <reified T> DataStoreEditor.setObject(key: String, value: T?) =
    setObject(key, value, object : com.google.gson.reflect.TypeToken<T>() {}.type)
