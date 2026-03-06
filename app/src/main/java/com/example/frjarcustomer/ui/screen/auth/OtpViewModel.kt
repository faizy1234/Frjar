package com.example.frjarcustomer.ui.screen.auth

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.frjarcustomer.navigation.routes.AppRoute
import com.example.frjarcustomer.navigation.utils.CustomNavType
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
    private val savedStateHandle: SavedStateHandle
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

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}