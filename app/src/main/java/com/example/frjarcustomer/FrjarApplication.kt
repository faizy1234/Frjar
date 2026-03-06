package com.example.frjarcustomer

import android.app.Application
import com.example.frjarcustomer.appstate.SnackbarController
import com.example.frjarcustomer.core.di.ApplicationScope
import com.example.frjarcustomer.core.fcm.FcmRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class FrjarApplication : Application() {

    @Inject
    lateinit var fcmRepository: FcmRepository

    @Inject
    @ApplicationScope
    lateinit var applicationScope: CoroutineScope

    private val appScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        SnackbarController.init(applicationScope)
        appScope.launch { fcmRepository.refreshToken() }
    }
}
