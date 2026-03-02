package com.example.frjarcustomer.core.di

import com.example.frjarcustomer.appstate.AppLanguage
import com.example.frjarcustomer.data.local.datastore.AppDataStore
import com.example.frjarcustomer.data.local.datastore.PreferencesKeys
import com.example.frjarcustomer.data.local.datastore.getObject
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.runBlocking

@Singleton
class LanguageProvider @Inject constructor(
    private val dataStore: AppDataStore
) {

    fun getLanguageCode(): String = runBlocking {
        (dataStore.getObject<AppLanguage>(PreferencesKeys.APP_LANGUAGE).getOrNull() ?: AppLanguage.DEFAULT).languageCode
    }
}
