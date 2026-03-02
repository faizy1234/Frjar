package com.example.frjarcustomer.core.session

/**
 * Production-ready session holder for token and language.
 * In-memory for fast sync access in interceptors; persisted via DataStore.
 */
interface SessionManager {

    fun getToken(): String?
    fun getLanguage(): String?

    suspend fun setToken(token: String?)
    suspend fun setLanguage(language: String?)

    fun setLanguageInMemory(code: String?)

    /** Load token and language from DataStore into memory. Call at app start. */
    suspend fun loadFromDataStore()
}
