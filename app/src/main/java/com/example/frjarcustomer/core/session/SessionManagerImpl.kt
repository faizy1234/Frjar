package com.example.frjarcustomer.core.session

import com.example.frjarcustomer.core.di.ApplicationScope
import com.example.frjarcustomer.data.local.datastore.AppDataStore
import com.example.frjarcustomer.data.local.datastore.PreferencesKeys
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

    override suspend fun setLanguage(language: String?) {
        val value = language?.takeIf { it.isNotBlank() } ?: DEFAULT_LANGUAGE
        languageHolder.set(value)
        dataStore.putString(PreferencesKeys.LANGUAGE, value)
    }

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
