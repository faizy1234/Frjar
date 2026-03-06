package com.example.frjarcustomer.ui.screen.intro.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frjarcustomer.data.local.datastore.AppDataStore
import com.example.frjarcustomer.data.local.datastore.PreferencesKeys
import com.example.frjarcustomer.data.remote.model.appSetting.EssentialAppSetting
import com.example.frjarcustomer.data.remote.model.responseMaper.onboarding.OnboardingData
import com.example.frjarcustomer.data.remote.repository.Repository
import com.example.frjarcustomer.data.remote.utils.ApiResult
import com.example.frjarcustomer.data.remote.utils.AppUpdateStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SplashDestination {
    data class OnboardingDestination(val onboardingData: OnboardingData) : SplashDestination()
    object Auth : SplashDestination()
    object AppFeature : SplashDestination()
    data class VersionExpire(val updateStatus: AppUpdateStatus) : SplashDestination()
}

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val appDataStore: AppDataStore,
    private val repository: Repository

) : ViewModel() {
    private val _essentialAppSetting =
        MutableStateFlow(EssentialAppSetting())

    val essentialAppSetting: StateFlow<EssentialAppSetting> =
        _essentialAppSetting
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = EssentialAppSetting()
            )

    private val _destination = MutableSharedFlow<SplashDestination>(
        replay = 1
    )
    val destination: SharedFlow<SplashDestination> = _destination.asSharedFlow()

    private val _showNoConnection = MutableStateFlow(false)
    val showNoConnection: StateFlow<Boolean> = _showNoConnection.asStateFlow()


    private val _isDataLoaded = MutableStateFlow(false)
    val isDataLoaded: StateFlow<Boolean> = _isDataLoaded.asStateFlow()

    private var isAndroidMaintenanceMode: Boolean = false

    init {
        startSplashFlow()
    }

    fun retry() {
        _showNoConnection.value = false
        startSplashFlow()
    }


    private fun startSplashFlow() {
        viewModelScope.launch {
            delay(200L)
            val checkAuthToken =
                appDataStore.getString(PreferencesKeys.USER_TOKEN).getOrNull().isNullOrEmpty()
            if (checkAuthToken) {
                getAuthToken()
            } else {
                getAppSetting()
            }
        }
    }

    private fun getWalkThrough() {
        viewModelScope.launch {
            repository.getAllCustomerWalkThrough().collect { result ->
                when (result) {
                    is ApiResult.Success -> {
                        delay(300L)
                        _isDataLoaded.tryEmit(false)
                        delay(300L)
                        _destination.emit(SplashDestination.OnboardingDestination(result.data))


                    }

                    is ApiResult.Error -> if (result.isNoNetwork) {
                        _isDataLoaded.tryEmit(false)
                        _showNoConnection.value = true
                    } else {
                        _isDataLoaded.tryEmit(false)
                    }

                    is ApiResult.Loading -> {
                        _isDataLoaded.tryEmit(true)

                    }
                }
            }
        }
    }

    private fun getAuthToken() {
        viewModelScope.launch {
            repository.getAuthToken().collect { result ->
                when (result) {
                    is ApiResult.Success -> getAppSetting()
                    is ApiResult.Error -> if (result.isNoNetwork) {
                        _isDataLoaded.tryEmit(false)
                        _showNoConnection.value = true
                    } else {
                        _isDataLoaded.tryEmit(false)
                    }

                    is ApiResult.Loading -> {}
                }
            }
        }
    }

    private fun getAppSetting() {
        viewModelScope.launch {
            repository.getCustomerAppSetting().collect { result ->
                when (result) {
                    is ApiResult.Success -> {
                        val appSettingData = result.data.data
                        _essentialAppSetting.update {
                            it.copy(
                                contactNo = appSettingData?.customerSupportNo,
                                maintenanceMessageLocalized = appSettingData?.maintenanceMessageLocalized,
                                appUpdateMessageLocalized = appSettingData?.appUpdateMessageLocalized,
                                androidCustomerUrl = appSettingData?.androidCustomerUrl

                            )
                        }
                        isAndroidMaintenanceMode = appSettingData?.androidMaintainaceMode == true

                        getAppVersionInfo()
                    }

                    is ApiResult.Error -> if (result.isNoNetwork) {
                        _isDataLoaded.tryEmit(false)
                        _showNoConnection.value = true
                    } else {
                        _isDataLoaded.tryEmit(false)
                    }

                    is ApiResult.Loading -> {}
                }
            }
        }
    }

    private fun getAppVersionInfo() {
        viewModelScope.launch {
            repository.checkAppVersion().collect { result ->
                when (result) {
                    is ApiResult.Success -> {
                        val versionData = result.data.data
                        if (versionData?.isUpToDated == true) {
                            if (isAndroidMaintenanceMode) {
                                _destination.emit(SplashDestination.AppFeature)
                            } else {
                                val onboardingDone =
                                    appDataStore.getBoolean(PreferencesKeys.ONBOARDING_DONE)
                                        .getOrNull() == true
                                if (onboardingDone) {
                                    delay(600L)
                                    _destination.emit(SplashDestination.Auth)
                                } else {
                                    getWalkThrough()
                                }
                            }
                        } else {
                            val status = AppUpdateStatus.fromValue(versionData?.versionUpdateType)
                            _destination.emit(SplashDestination.VersionExpire(status))
                        }
                    }

                    is ApiResult.Error -> if (result.isNoNetwork) {
                        _isDataLoaded.tryEmit(false)
                        _showNoConnection.value = true
                    } else {
                        _isDataLoaded.tryEmit(false)
                    }

                    is ApiResult.Loading -> {}
                }
            }
        }
    }


    fun checkOnboardingStatus() {
        viewModelScope.launch {
            val onboardingDone =
                appDataStore.getBoolean(PreferencesKeys.ONBOARDING_DONE)
                    .getOrNull() == true
            if (onboardingDone) {
                delay(600L)
                _destination.emit(SplashDestination.Auth)
            } else {
                getWalkThrough()
            }
        }


    }
}



