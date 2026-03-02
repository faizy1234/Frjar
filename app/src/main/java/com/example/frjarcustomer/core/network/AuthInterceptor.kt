package com.example.frjarcustomer.core.network

import com.example.frjarcustomer.core.config.AppConfig
import com.example.frjarcustomer.core.fcm.FcmRepository
import com.example.frjarcustomer.core.session.SessionManager
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val appConfig: AppConfig,
    private val sessionManager: SessionManager,
    private val fcmRepository: FcmRepository
) : Interceptor {

    private val apiHost: String? by lazy {
        appConfig.baseUrl.toHttpUrlOrNull()?.host
    }

    private val deviceId: String? by lazy {
        runBlocking { fcmRepository.getDeviceId() }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (!isApiRequest(request)) {
            return chain.proceed(request)
        }

        val token = sessionManager.getToken()
        val language = sessionManager.getLanguage() ?: "en"

        val newBuilder = request.newBuilder()
            .addHeader(CONTENT_TYPE, CONTENT_TYPE_JSON)
            .addHeader(HEADER_ACCEPT, HEADER_ACCEPT)
            .addHeader(HEADER_ACCEPT_LANGUAGE, language)
            .addHeader(SEGMENT_TYPE, SEGMENT_TYPE_VALUE)
        deviceId?.takeIf { it.isNotBlank() }?.let { id ->
            newBuilder.addHeader(HEADER_DEVICE_ID, id)
        }
        if (!token.isNullOrBlank()) {
            newBuilder.addHeader(HEADER_AUTHORIZATION, "$BEARER_PREFIX$token")
        }

        return chain.proceed(newBuilder.build())
    }

    private fun isApiRequest(request: okhttp3.Request): Boolean {
        val host = request.url.host
        val api = apiHost ?: return true
        return host == api
    }

    companion object {
        private const val HEADER_AUTHORIZATION = "Authorization"
        private const val CONTENT_TYPE = "Content-Typ"
        private const val HEADER_ACCEPT = "Accept"
        private const val HEADER_ACCEPT_LANGUAGE = "app_lang"
        private const val HEADER_DEVICE_ID = "device_id"
        private const val CONTENT_TYPE_JSON = "application/json"
        private const val SEGMENT_TYPE = "segment_type"
        const val SEGMENT_TYPE_VALUE = "RETAIL"
        private const val BEARER_PREFIX = "Bearer "
        const val PLATFORM_VALUE = "ANDROID"
    }
}
