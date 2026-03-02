package com.example.frjarcustomer.data.remote.repository.fcm

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import android.util.Log
import com.example.frjarcustomer.core.di.ApplicationScope
import com.example.frjarcustomer.core.fcm.FcmRepository
import com.example.frjarcustomer.data.local.datastore.AppDataStore
import com.example.frjarcustomer.data.local.datastore.PreferencesKeys
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FcmRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dataStore: AppDataStore,
    @ApplicationScope private val applicationScope: CoroutineScope
) : FcmRepository {

    companion object {
        private const val CHECKERBOARD = "FcmRepo"
    }

    init {
        applicationScope.launch { ensureDeviceIdFetched() }
    }

    override fun getTokenFlow(): Flow<String?> =
        dataStore.getStringFlow(PreferencesKeys.FCM_TOKEN).map { it?.takeIf { s -> s.isNotBlank() } }

    override fun getDeviceIdFlow(): Flow<String?> =
        dataStore.getStringFlow(PreferencesKeys.DEVICE_ID).map { it?.takeIf { s -> s.isNotBlank() } }

    override suspend fun getCurrentToken(): String? =
        dataStore.getString(PreferencesKeys.FCM_TOKEN).getOrNull()?.takeIf { it.isNotBlank() }

    override suspend fun getDeviceId(): String? =
        dataStore.getString(PreferencesKeys.DEVICE_ID).getOrNull()?.takeIf { it.isNotBlank() }

    override suspend fun setToken(token: String) {
        dataStore.putString(PreferencesKeys.FCM_TOKEN, token)
        Log.d(CHECKERBOARD, "FCM token received and saved: ${token.take(20)}...")
    }

    override suspend fun refreshToken(): String? {
        val token = FirebaseMessaging.getInstance().token.await().takeIf { it.isNotBlank() }
        if (token != null) {
            dataStore.putString(PreferencesKeys.FCM_TOKEN, token)
        } else {
            Log.d(CHECKERBOARD, "FCM token refresh: token null or blank")
        }
        return token
    }

    @SuppressLint("HardwareIds")
    override suspend fun ensureDeviceIdFetched() {

        val id = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        if (!id.isNullOrBlank()) {
            dataStore.putString(PreferencesKeys.DEVICE_ID, id)
            Log.d(CHECKERBOARD, "Device ID received and saved: $id")
        } else {
            Log.w(CHECKERBOARD, "Device ID: ANDROID_ID was null or blank")
        }
    }
}
