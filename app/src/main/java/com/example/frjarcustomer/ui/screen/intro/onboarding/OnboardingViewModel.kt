package com.example.frjarcustomer.ui.screen.intro.onboarding

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.frjarcustomer.data.local.datastore.AppDataStore
import com.example.frjarcustomer.data.local.datastore.PreferencesKeys
import com.example.frjarcustomer.data.remote.model.responseMaper.onboarding.OnboardingData
import com.example.frjarcustomer.data.remote.repository.Repository
import com.example.frjarcustomer.navigation.routes.AppRoute
import com.example.frjarcustomer.navigation.utils.CustomNavType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.reflect.typeOf

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val appDataStore: AppDataStore,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _navigateToAuth = MutableSharedFlow<Unit>(
        replay = 1
    )
    val navigateToAuth: SharedFlow<Unit> = _navigateToAuth.asSharedFlow()
    val onboardingData: OnboardingData = savedStateHandle.toRoute<AppRoute.Onboarding>(
        typeMap = mapOf(
            typeOf<OnboardingData>() to CustomNavType(
                OnboardingData::class.java,
                OnboardingData.serializer()
            )
        )
    ).pages



    fun onFinish() {
        viewModelScope.launch {
            appDataStore.putBoolean(PreferencesKeys.ONBOARDING_DONE, true)
            _navigateToAuth.emit(Unit)
        }
    }




}
