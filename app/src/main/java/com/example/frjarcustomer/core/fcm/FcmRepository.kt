package com.example.frjarcustomer.core.fcm

import kotlinx.coroutines.flow.Flow

interface FcmRepository {

    fun getTokenFlow(): Flow<String?>
    fun getDeviceIdFlow(): Flow<String?>

    suspend fun getCurrentToken(): String?
    suspend fun getDeviceId(): String?

    suspend fun setToken(token: String)
    suspend fun refreshToken(): String?
    suspend fun ensureDeviceIdFetched()
}
