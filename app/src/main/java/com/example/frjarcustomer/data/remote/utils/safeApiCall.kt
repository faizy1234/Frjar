package com.example.frjarcustomer.data.remote.utils

import com.example.frjarcustomer.R
import com.example.frjarcustomer.core.di.StringProvider

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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
            else -> e.localizedMessage ?: stringProvider.getString(R.string.unknown_error)
        }
        emit(ApiResult.Error(errorMessage, getErrorCode(e)))
    }
}.flowOn(dispatcher)


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

