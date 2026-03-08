package com.example.frjarcustomer.core.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.example.frjarcustomer.data.local.datastore.AppDataStore
import com.example.frjarcustomer.data.local.datastore.PreferencesKeys
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import com.google.android.gms.location.LocationServices

@Singleton
class LocationStoreHelper @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val dataStore: AppDataStore
) {

    private val fusedClient by lazy {
        LocationServices.getFusedLocationProviderClient(appContext)
    }

    fun hasLocationPermission(): Boolean =
        ContextCompat.checkSelfPermission(appContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

    suspend fun fetchAndSaveLocation() = withContext(Dispatchers.IO) {
        if (!hasLocationPermission()) return@withContext
        val location = suspendCancellableCoroutine { cont ->
            fusedClient.lastLocation
                .addOnSuccessListener { loc ->
                    cont.resume(loc)
                }
                .addOnFailureListener {
                    cont.resume(null)
                }
        }
        location?.let { loc ->
            dataStore.putString(PreferencesKeys.LATITUDE, loc.latitude.toString())
            dataStore.putString(PreferencesKeys.LONGITUDE, loc.longitude.toString())
        }
    }
}
