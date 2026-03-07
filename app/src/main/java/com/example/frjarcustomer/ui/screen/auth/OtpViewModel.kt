package com.example.frjarcustomer.ui.screen.auth

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.frjarcustomer.appstate.MessageContent
import com.example.frjarcustomer.appstate.MessageType
import com.example.frjarcustomer.appstate.SnackbarController
import com.example.frjarcustomer.appstate.SnackbarModel
import com.example.frjarcustomer.data.remote.repository.Repository
import com.example.frjarcustomer.data.remote.utils.ApiResult
import com.example.frjarcustomer.navigation.routes.AppRoute
import com.example.frjarcustomer.navigation.utils.CustomNavType
import com.example.frjarcustomer.ui.components.AuthValidation
import com.example.frjarcustomer.ui.components.ValidationRules
import com.example.frjarcustomer.ui.components.ValidationShakeState
import com.example.frjarcustomer.ui.screen.auth.otpScreen.contentHolder.OtpScreenContent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.reflect.typeOf

@HiltViewModel
class OtpViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: Repository
) : ViewModel() {

    private val typeMap = mapOf(
        typeOf<OtpScreenContent>() to CustomNavType(
            OtpScreenContent::class.java,
            OtpScreenContent.serializer()
        )
    )

    val otpScreenContent: OtpScreenContent = runCatching {
        savedStateHandle.toRoute<AppRoute.LoginContainer>(typeMap)
    }.getOrNull()?.content ?: savedStateHandle.toRoute<AppRoute.OtpScreen>(typeMap).content

    private val _otp = MutableStateFlow("")
    val otp: StateFlow<String> = _otp.asStateFlow()

    private val _countdownSeconds = MutableStateFlow(90)
    val countdownSeconds: StateFlow<Int> = _countdownSeconds.asStateFlow()

    private val _validationShake = MutableStateFlow(ValidationShakeState())
    val validationShake: StateFlow<ValidationShakeState> = _validationShake.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private var timerJob: Job? = null

    init {
        startTimer()
    }

    fun setOtp(value: String) {
        _otp.update { value.filter { it.isDigit() }.take(4) }
    }

    fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_countdownSeconds.value > 0) {
                delay(1000L)
                _countdownSeconds.update { it - 1 }
            }
        }
    }

    fun resetTimer() {
        _countdownSeconds.update { 90 }
        startTimer()
    }

    fun resendOtp() {
        viewModelScope.launch {
            _isLoading.update { true }
            repository.userResendOtp().collect { apiResult ->
                when (apiResult) {
                    is ApiResult.Loading -> {

                    }

                    is ApiResult.Success -> {
                        _isLoading.update { false }
                        SnackbarController.show(
                            SnackbarModel(
                                type = MessageType.SUCCESS,
                                message = MessageContent.PlainString(apiResult.data.appUpdateMessageLocalized)
                            )
                        )
                        resetTimer()

                    }

                    is ApiResult.Error -> {
                        _isLoading.update { false }
                        SnackbarController.show(
                            SnackbarModel(
                                type = MessageType.ERROR,
                                message = MessageContent.PlainString(apiResult.message)
                            )
                        )
                    }
                }
            }
        }
    }

    fun validateOtpAndProceed(onSuccess: () -> Unit) {
        val result = AuthValidation.validate(
            listOf(
                _otp.value to listOf(
                    ValidationRules.required("Verification code is required"),
                    ValidationRules.custom("Please enter 4-digit verification code") { it.length == 4 }
                )
            )
        )
        if (result != null) {
            SnackbarController.show(
                SnackbarModel(
                    type = MessageType.ERROR,
                    message = MessageContent.PlainString(result.firstErrorMessage)
                )
            )
            _validationShake.update {
                ValidationShakeState(it.triggerId + 1, result.invalidIndices.toSet())
            }
        } else {
            viewModelScope.launch {
                _isLoading.update { true }
                repository.loginWithPhone(
                    phoneNumber = otpScreenContent.number,
                    password = null,
                    otp = _otp.value,
                    isPasswordSignIn = false
                ).collect { apiResult ->
                    when (apiResult) {
                        is ApiResult.Loading -> {}
                        is ApiResult.Success -> {
                            _isLoading.update { false }
                            onSuccess()
                        }

                        is ApiResult.Error -> {
                            _isLoading.update { false }
                            SnackbarController.show(
                                SnackbarModel(
                                    type = MessageType.ERROR,
                                    message = MessageContent.PlainString(apiResult.message)
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}