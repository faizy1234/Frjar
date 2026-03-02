package com.example.frjarcustomer.data.local.datastore

sealed class DataStoreResult<out T> {

    data class Success<T>(val value: T) : DataStoreResult<T>()
    data class Error(val exception: Throwable) : DataStoreResult<Nothing>() {
        val message: String get() = exception.message.orEmpty()
    }

    val isSuccess: Boolean get() = this is Success
    val isFailure: Boolean get() = this is Error
    fun getOrNull(): T? = (this as? Success)?.value
    fun exceptionOrNull(): Throwable? = (this as? Error)?.exception
    fun getOrElse(default: @UnsafeVariance T): T = getOrNull() ?: default
    fun getOrThrow(): T = getOrNull() ?: throw (this as Error).exception
}

inline fun <T> DataStoreResult<T>.onSuccess(action: (T) -> Unit): DataStoreResult<T> {
    if (this is DataStoreResult.Success) action(value)
    return this
}
inline fun <T> DataStoreResult<T>.onFailure(action: (Throwable) -> Unit): DataStoreResult<T> {
    if (this is DataStoreResult.Error) action(exception)
    return this
}
