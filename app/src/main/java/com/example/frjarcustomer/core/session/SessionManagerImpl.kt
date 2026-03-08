package com.example.frjarcustomer.core.session

import com.example.frjarcustomer.core.di.ApplicationScope
import com.example.frjarcustomer.data.local.datastore.AppDataStore
import com.example.frjarcustomer.data.local.datastore.PreferencesKeys
import com.example.frjarcustomer.data.local.datastore.getObject
import com.example.frjarcustomer.data.local.datastore.putObject
import com.example.frjarcustomer.data.remote.dto.response.user.UserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManagerImpl @Inject constructor(
    private val dataStore: AppDataStore,
    @ApplicationScope private val applicationScope: CoroutineScope
) : SessionManager {

    private val tokenHolder = AtomicReference<String?>(null)
    private val languageHolder = AtomicReference<String?>(null)

    init {
        applicationScope.launch { loadFromDataStore() }
    }

    override fun getToken(): String? = tokenHolder.get()

    override fun getLanguage(): String? = languageHolder.get() ?: DEFAULT_LANGUAGE

    override suspend fun setToken(token: String?) {
        tokenHolder.set(token)
        dataStore.putString(PreferencesKeys.USER_TOKEN, token)
    }

    override suspend fun getUser(): UserResponse? =
        dataStore.getObject<UserResponse>(PreferencesKeys.USER).getOrNull()

    override suspend fun setUser(user: UserResponse?) {
        dataStore.putObject(PreferencesKeys.USER, user)
    }

    override suspend fun setLanguage(language: String?) {
        val value = language?.takeIf { it.isNotBlank() } ?: DEFAULT_LANGUAGE
        languageHolder.set(value)
        dataStore.putString(PreferencesKeys.LANGUAGE, value)
    }

    override suspend fun getLatitude(): String? =
        dataStore.getString(PreferencesKeys.LATITUDE).getOrNull()

    override suspend fun getLongitude(): String? =
        dataStore.getString(PreferencesKeys.LONGITUDE).getOrNull()

    override fun setLanguageInMemory(code: String?) {
        languageHolder.set(code?.takeIf { it.isNotBlank() } ?: DEFAULT_LANGUAGE)
    }

    override suspend fun loadFromDataStore() {
        val tokenResult = dataStore.getString(PreferencesKeys.USER_TOKEN)
        tokenHolder.set(tokenResult.getOrNull())

        val langResult = dataStore.getString(PreferencesKeys.LANGUAGE)
        languageHolder.set(langResult.getOrNull() ?: DEFAULT_LANGUAGE)
    }

    companion object {
        private const val DEFAULT_LANGUAGE = "en"
    }
}
