package com.example.frjarcustomer.core.di

import com.example.frjarcustomer.appstate.AppLanguage
import com.example.frjarcustomer.core.session.SessionManager
import com.example.frjarcustomer.data.local.datastore.AppDataStore
import com.example.frjarcustomer.data.local.datastore.PreferencesKeys
import com.example.frjarcustomer.data.local.datastore.getObjectFlow
import com.example.frjarcustomer.data.local.datastore.putObject
import com.example.frjarcustomer.utils.LocaleManager
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@Singleton
class LanguageRepository @Inject constructor(
    private val dataStore: AppDataStore,
    private val sessionManager: SessionManager
) {
    private val repositoryScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val _currentLanguage = MutableStateFlow(AppLanguage.DEFAULT)

    val currentLanguage: StateFlow<AppLanguage> = _currentLanguage.asStateFlow()

    init {
        repositoryScope.launch {
            dataStore.getObjectFlow<AppLanguage>(PreferencesKeys.APP_LANGUAGE)
                .distinctUntilChanged()
                .catch { _currentLanguage.value = AppLanguage.DEFAULT }
                .collect { lang ->
                    val resolved = lang ?: AppLanguage.DEFAULT
                    LocaleManager.init(resolved.languageCode)
                    _currentLanguage.value = resolved
                    sessionManager.setLanguageInMemory(resolved.languageCode)
                }
        }
    }

    suspend fun setLanguage(language: AppLanguage) {
        LocaleManager.init(language.languageCode)
        _currentLanguage.value = language
        dataStore.putObject(PreferencesKeys.APP_LANGUAGE, language)
        sessionManager.setLanguageInMemory(language.languageCode)
    }
}
