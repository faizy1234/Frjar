package com.example.frjarcustomer.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton

private const val DATASTORE_FILE_NAME = "app_preferences.pb"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_FILE_NAME)

@Singleton
class AppDataStoreImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val serializers: DataStoreSerializers
) : AppDataStore {

    private val store: DataStore<Preferences> = context.dataStore
    private val objectKeyPrefix = "obj:"

    override suspend fun getString(key: String): DataStoreResult<String?> =
        runCatching { store.data.map { it[stringPreferencesKey(key)] }.first() }
            .fold(onSuccess = { DataStoreResult.Success(it) }, onFailure = { DataStoreResult.Error(it) })

    override fun getStringFlow(key: String): Flow<String?> = store.data.map { it[stringPreferencesKey(key)] }

    override suspend fun putString(key: String, value: String?): DataStoreResult<Unit> =
        editOne { if (value == null) remove(stringPreferencesKey(key)) else this[stringPreferencesKey(key)] = value }

    override suspend fun getInt(key: String): DataStoreResult<Int?> =
        runCatching { store.data.map { it[intPreferencesKey(key)] }.first() }
            .fold(onSuccess = { DataStoreResult.Success(it) }, onFailure = { DataStoreResult.Error(it) })
    override fun getIntFlow(key: String): Flow<Int?> = store.data.map { it[intPreferencesKey(key)] }
    override suspend fun putInt(key: String, value: Int?): DataStoreResult<Unit> =
        editOne { if (value == null) remove(intPreferencesKey(key)) else this[intPreferencesKey(key)] = value }

    override suspend fun getLong(key: String): DataStoreResult<Long?> =
        runCatching { store.data.map { it[longPreferencesKey(key)] }.first() }
            .fold(onSuccess = { DataStoreResult.Success(it) }, onFailure = { DataStoreResult.Error(it) })
    override fun getLongFlow(key: String): Flow<Long?> = store.data.map { it[longPreferencesKey(key)] }
    override suspend fun putLong(key: String, value: Long?): DataStoreResult<Unit> =
        editOne { if (value == null) remove(longPreferencesKey(key)) else this[longPreferencesKey(key)] = value }

    override suspend fun getBoolean(key: String): DataStoreResult<Boolean?> =
        runCatching { store.data.map { it[booleanPreferencesKey(key)] }.first() }
            .fold(onSuccess = { DataStoreResult.Success(it) }, onFailure = { DataStoreResult.Error(it) })
    override fun getBooleanFlow(key: String): Flow<Boolean?> = store.data.map { it[booleanPreferencesKey(key)] }
    override suspend fun putBoolean(key: String, value: Boolean?): DataStoreResult<Unit> =
        editOne { if (value == null) remove(booleanPreferencesKey(key)) else this[booleanPreferencesKey(key)] = value }

    override suspend fun getFloat(key: String): DataStoreResult<Float?> =
        runCatching { store.data.map { it[floatPreferencesKey(key)] }.first() }
            .fold(onSuccess = { DataStoreResult.Success(it) }, onFailure = { DataStoreResult.Error(it) })
    override fun getFloatFlow(key: String): Flow<Float?> = store.data.map { it[floatPreferencesKey(key)] }
    override suspend fun putFloat(key: String, value: Float?): DataStoreResult<Unit> =
        editOne { if (value == null) remove(floatPreferencesKey(key)) else this[floatPreferencesKey(key)] = value }

    override suspend fun getStringSet(key: String): DataStoreResult<Set<String>?> =
        runCatching { store.data.map { it[stringSetPreferencesKey(key)] }.first() }
            .fold(onSuccess = { DataStoreResult.Success(it) }, onFailure = { DataStoreResult.Error(it) })
    override fun getStringSetFlow(key: String): Flow<Set<String>?> = store.data.map { it[stringSetPreferencesKey(key)] }
    override suspend fun putStringSet(key: String, value: Set<String>?): DataStoreResult<Unit> =
        editOne { if (value == null) remove(stringSetPreferencesKey(key)) else this[stringSetPreferencesKey(key)] = value }

    override suspend fun <T> getObject(key: String, type: Type): DataStoreResult<T?> {
        val raw = getString(objectKeyPrefix + key).getOrNull() ?: return DataStoreResult.Success(null)
        return DataStoreResult.Success(serializers.fromJsonOrNull<T>(raw, type))
    }
    override fun <T> getObjectFlow(key: String, type: Type): Flow<T?> =
        getStringFlow(objectKeyPrefix + key).map { serializers.fromJsonOrNull<T>(it, type) }
    override suspend fun <T> putObject(key: String, value: T?, type: Type): DataStoreResult<Unit> {
        return putString(objectKeyPrefix + key, value?.let { serializers.toJson(it, type) })
    }

    override suspend fun remove(key: String): DataStoreResult<Unit> = editOne {
        remove(stringPreferencesKey(key))
        remove(intPreferencesKey(key))
        remove(longPreferencesKey(key))
        remove(booleanPreferencesKey(key))
        remove(floatPreferencesKey(key))
        remove(stringSetPreferencesKey(key))
        remove(stringPreferencesKey(objectKeyPrefix + key))
    }

    override suspend fun clear(): DataStoreResult<Unit> = editOne { clear() }

    override suspend fun edit(block: suspend DataStoreEditor.() -> Unit): DataStoreResult<Unit> {
        val edits = mutableListOf<androidx.datastore.preferences.core.MutablePreferences.() -> Unit>()
        val editor = object : DataStoreEditor {
            override fun setString(key: String, value: String?) {
                edits.add { if (value == null) remove(stringPreferencesKey(key)) else this[stringPreferencesKey(key)] = value }
            }
            override fun setInt(key: String, value: Int?) {
                edits.add { if (value == null) remove(intPreferencesKey(key)) else this[intPreferencesKey(key)] = value }
            }
            override fun setLong(key: String, value: Long?) {
                edits.add { if (value == null) remove(longPreferencesKey(key)) else this[longPreferencesKey(key)] = value }
            }
            override fun setBoolean(key: String, value: Boolean?) {
                edits.add { if (value == null) remove(booleanPreferencesKey(key)) else this[booleanPreferencesKey(key)] = value }
            }
            override fun setFloat(key: String, value: Float?) {
                edits.add { if (value == null) remove(floatPreferencesKey(key)) else this[floatPreferencesKey(key)] = value }
            }
            override fun setStringSet(key: String, value: Set<String>?) {
                edits.add { if (value == null) remove(stringSetPreferencesKey(key)) else this[stringSetPreferencesKey(key)] = value }
            }
            override fun <T> setObject(key: String, value: T?, type: Type) {
                val json = value?.let { serializers.toJson(it, type) }
                edits.add {
                    val k = stringPreferencesKey(objectKeyPrefix + key)
                    if (json == null) remove(k) else this[k] = json
                }
            }
            override fun remove(key: String) {
                edits.add {
                    remove(stringPreferencesKey(key))
                    remove(intPreferencesKey(key))
                    remove(longPreferencesKey(key))
                    remove(booleanPreferencesKey(key))
                    remove(floatPreferencesKey(key))
                    remove(stringSetPreferencesKey(key))
                    remove(stringPreferencesKey(objectKeyPrefix + key))
                }
            }
        }
        editor.block()
        return runCatching {
            store.edit { prefs -> edits.forEach { prefs.run(it) } }
        }.fold(onSuccess = { DataStoreResult.Success(Unit) }, onFailure = { DataStoreResult.Error(it) })
    }

    private suspend fun editOne(transform: androidx.datastore.preferences.core.MutablePreferences.() -> Unit): DataStoreResult<Unit> =
        runCatching { store.edit(transform) }.fold(
            onSuccess = { DataStoreResult.Success(Unit) },
            onFailure = { DataStoreResult.Error(it) }
        )
}
