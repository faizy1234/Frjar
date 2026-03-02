package com.example.frjarcustomer.core.config

import com.example.frjarcustomer.BuildConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BuildConfigProvider @Inject constructor() : AppConfig {

    override val baseUrl: String
        get() = BuildConfig.BASE_URL

    override val envName: String
        get() = BuildConfig.ENV_NAME

    override val isDebug: Boolean
        get() = BuildConfig.IS_DEBUG

    override val isNetworkLoggingEnabled: Boolean
        get() = BuildConfig.LOG_NETWORK

    override val versionName: String
        get() = BuildConfig.VERSION_NAME
}
