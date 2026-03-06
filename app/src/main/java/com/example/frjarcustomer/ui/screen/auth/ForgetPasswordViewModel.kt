package com.example.frjarcustomer.ui.screen.auth

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

enum class ForgetPasswordStep {
    MethodSelect,
    InputEmail,
    InputPhone,
    VerifyOtp,
    NewPassword,
    Success
}

@HiltViewModel
class ForgetPasswordViewModel @Inject constructor() : ViewModel() {

    private val _step = MutableStateFlow(ForgetPasswordStep.MethodSelect)
    val step: StateFlow<ForgetPasswordStep> = _step.asStateFlow()

    private val _selectedMethodEmail = MutableStateFlow(true)
    val selectedMethodEmail: StateFlow<Boolean> = _selectedMethodEmail.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _phone = MutableStateFlow("")
    val phone: StateFlow<String> = _phone.asStateFlow()

    private val _otp = MutableStateFlow("")
    val otp: StateFlow<String> = _otp.asStateFlow()

    private val _countdownSeconds = MutableStateFlow(48)
    val countdownSeconds: StateFlow<Int> = _countdownSeconds.asStateFlow()

    private val _newPassword = MutableStateFlow("")
    val newPassword: StateFlow<String> = _newPassword.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword.asStateFlow()

    private val _newPasswordVisible = MutableStateFlow(false)
    val newPasswordVisible: StateFlow<Boolean> = _newPasswordVisible.asStateFlow()

    private val _confirmPasswordVisible = MutableStateFlow(false)
    val confirmPasswordVisible: StateFlow<Boolean> = _confirmPasswordVisible.asStateFlow()

    fun selectMethodEmail(isEmail: Boolean) {
        _selectedMethodEmail.update { isEmail }
    }

    fun setEmail(value: String) = _email.update { value }
    fun setPhone(value: String) = _phone.update { value.filter { it.isDigit() || it == '+' } }
    fun setOtp(value: String) = _otp.update { value.filter { it.isDigit() }.take(4) }
    fun setNewPassword(value: String) = _newPassword.update { value }
    fun setConfirmPassword(value: String) = _confirmPassword.update { value }
    fun toggleNewPasswordVisible() = _newPasswordVisible.update { !it }
    fun toggleConfirmPasswordVisible() = _confirmPasswordVisible.update { !it }
    fun setCountdownSeconds(seconds: Int) = _countdownSeconds.update { seconds.coerceAtLeast(0) }

    fun onContinueFromMethod() {
        _step.update {
            if (_selectedMethodEmail.value) ForgetPasswordStep.InputEmail else ForgetPasswordStep.InputPhone
        }
    }

    fun onSendCode() {
        _step.update { ForgetPasswordStep.VerifyOtp }
        _countdownSeconds.update { 48 }
    }

    fun onVerifyOtpContinue() {
        _step.update { ForgetPasswordStep.NewPassword }
    }

    fun onConfirmNewPassword() {
        _step.update { ForgetPasswordStep.Success }
    }

    fun onBack() {
        _step.update { current ->
            when (current) {
                ForgetPasswordStep.MethodSelect -> current
                ForgetPasswordStep.InputEmail, ForgetPasswordStep.InputPhone -> ForgetPasswordStep.MethodSelect
                ForgetPasswordStep.VerifyOtp -> if (_selectedMethodEmail.value) ForgetPasswordStep.InputEmail else ForgetPasswordStep.InputPhone
                ForgetPasswordStep.NewPassword -> ForgetPasswordStep.VerifyOtp
                ForgetPasswordStep.Success -> ForgetPasswordStep.NewPassword
            }
        }
    }
}
