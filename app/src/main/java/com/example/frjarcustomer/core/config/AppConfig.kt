package com.example.frjarcustomer.core.config

/**
 * Environment-agnostic app configuration (debug/release).
 * Injected via Hilt; implementation reads from BuildConfig.
 */
interface AppConfig {
    val baseUrl: String
    val envName: String
    val isDebug: Boolean
    val isNetworkLoggingEnabled: Boolean
    val versionName: String
}
