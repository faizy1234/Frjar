package com.example.frjarcustomer.data.remote.utils

import com.example.frjarcustomer.R
import com.example.frjarcustomer.core.di.LanguageProvider
import com.example.frjarcustomer.core.di.StringProvider
import com.example.frjarcustomer.data.remote.dto.response.baseResponse.ApiErrorBody
import com.example.frjarcustomer.data.remote.dto.response.baseResponse.BaseResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.net.ConnectException
import java.io.IOException
import javax.net.ssl.SSLHandshakeException
import retrofit2.HttpException


fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T,
    stringProvider: StringProvider,
    languageCode: String? = null
): Flow<ApiResult<T>> = flow {
    emit(ApiResult.Loading)
    try {
        val result = apiCall()
        emit(ApiResult.Success(result))
    } catch (e: Exception) {
        val errorMessage = when (e) {
            is SocketTimeoutException -> stringProvider.getString(R.string.timeout_error)
            is SSLHandshakeException -> stringProvider.getString(R.string.ssl_error)
            is UnknownHostException -> stringProvider.getString(R.string.network_unavailable)
            is ConnectException -> stringProvider.getString(R.string.connection_failed)
            is IOException -> stringProvider.getString(R.string.network_error)
            is TimeoutCancellationException -> stringProvider.getString(R.string.operation_timeout)
            is CancellationException -> throw e
            is HttpException -> parseBackendError(e, languageCode) ?: e.message
            ?: stringProvider.getString(R.string.unknown_error)

            else -> e.localizedMessage ?: stringProvider.getString(R.string.unknown_error)
        }
        emit(ApiResult.Error(errorMessage, getErrorCode(e)))
    }
}.flowOn(dispatcher)


fun <T> Flow<ApiResult<com.example.frjarcustomer.data.remote.dto.response.baseResponse.BaseResponse<T>>>.ensureSuccessCode(
    languageProvider: LanguageProvider,
    stringProvider: StringProvider
): Flow<ApiResult<com.example.frjarcustomer.data.remote.dto.response.baseResponse.BaseResponse<T>>> = map { result ->
    when (result) {
        is ApiResult.Success -> {
            val res = result.data
            if (res.code == 1) result
            else ApiResult.Error(
                message = baseResponseMessageForLocale(
                    res,
                    languageProvider.getLanguageCode(),
                    stringProvider
                ),
                responseCode = res.code ?: 0
            )
        }

        is ApiResult.Error -> result
        is ApiResult.Loading -> result
    }
}

private fun baseResponseMessageForLocale(
    res: com.example.frjarcustomer.data.remote.dto.response.baseResponse.BaseResponse<*>,
    lang: String,
    stringProvider: StringProvider
): String =
    when (lang) {
        AppLanguage.ARABIC.value -> res.arMessage
        AppLanguage.URDU.value -> res.urMessage
        AppLanguage.HINDI.value -> res.hiMessage
        else -> res.enMessage
    }.orEmpty().ifBlank { res.enMessage.orEmpty() }
        .ifBlank { stringProvider.getString(R.string.unknown_error) }

private fun parseBackendError(e: HttpException, languageCode: String?): String? {
    return try {
        val body = e.response()?.errorBody()?.string() ?: return null
        val json = org.json.JSONObject(body)
        val errorBody =
            _root_ide_package_.com.example.frjarcustomer.data.remote.dto.response.baseResponse.ApiErrorBody(
                error = json.optString("error").ifBlank { null },
                code = if (json.has("code")) json.optInt("code", 0).takeIf { it != 0 } else null,
                enMessage = json.optString("en_message").ifBlank { null },
                arMessage = json.optString("ar_message").ifBlank { null },
                urMessage = json.optString("ur_message").ifBlank { null },
                hiMessage = json.optString("hi_message").ifBlank { null }
            )
        errorBody.messageFor(languageCode ?: "en").ifBlank { null }
    } catch (_: Exception) {
        null
    }
}

private fun getErrorCode(e: Exception): Int {
    return when (e) {
        is HttpException -> e.code()
        is SSLHandshakeException -> 495
        is SocketTimeoutException -> 408
        is UnknownHostException -> ApiResult.ERROR_CODE_NO_NETWORK
        is ConnectException -> ApiResult.ERROR_CODE_NO_NETWORK
        is IOException -> ApiResult.ERROR_CODE_NO_NETWORK
        is TimeoutCancellationException -> 408
        else -> 0
    }
}

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val message: String, val responseCode: Int? = 0) : ApiResult<Nothing>() {
        val isNoNetwork: Boolean get() = responseCode == ERROR_CODE_NO_NETWORK
    }

    data object Loading : ApiResult<Nothing>()

    companion object {
        const val ERROR_CODE_NO_NETWORK = 499
    }
}

