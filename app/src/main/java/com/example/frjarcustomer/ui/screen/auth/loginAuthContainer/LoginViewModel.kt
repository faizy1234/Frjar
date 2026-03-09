package com.example.frjarcustomer.ui.screen.auth.loginAuthContainer

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.frjarcustomer.R
import com.example.frjarcustomer.appstate.MessageContent
import com.example.frjarcustomer.appstate.MessageType
import com.example.frjarcustomer.appstate.SnackbarController
import com.example.frjarcustomer.appstate.SnackbarModel
import com.example.frjarcustomer.core.di.StringProvider
import com.example.frjarcustomer.data.remote.repository.Repository
import com.example.frjarcustomer.data.remote.utils.ApiResult
import com.example.frjarcustomer.navigation.routes.AppRoute
import com.example.frjarcustomer.navigation.utils.CustomNavType
import com.example.frjarcustomer.ui.components.AuthValidation
import com.example.frjarcustomer.ui.components.ValidationRules
import com.example.frjarcustomer.ui.components.ValidationShakeState
import com.example.frjarcustomer.ui.screen.auth.otpScreen.contentHolder.OtpScreenContent
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.reflect.typeOf

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: Repository,
    private val savedStateHandle: SavedStateHandle,
    private val stringProvider: StringProvider
) : ViewModel() {

    private val typeMap = mapOf(
        typeOf<OtpScreenContent>() to CustomNavType(
            OtpScreenContent::class.java,
            OtpScreenContent.serializer()
        )
    )

    val otpScreenContent: OtpScreenContent =
        savedStateHandle.toRoute<AppRoute.OtpScreen>(typeMap).content


    private val _pagerPage = MutableStateFlow(0)
    val pagerPage: StateFlow<Int> = _pagerPage.asStateFlow()

    private val _mobileNumber = MutableStateFlow(otpScreenContent.number ?: "")
    val mobileNumber: StateFlow<String> = _mobileNumber.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _passwordVisible = MutableStateFlow(false)
    val passwordVisible: StateFlow<Boolean> = _passwordVisible.asStateFlow()

    private val _submitButtonPressed = MutableStateFlow(false)
    val submitButtonPressed: StateFlow<Boolean> = _submitButtonPressed.asStateFlow()

    private val _validationShake = MutableStateFlow(ValidationShakeState())
    val validationShake: StateFlow<ValidationShakeState> = _validationShake.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun setPagerPage(page: Int) {
        _pagerPage.update { page.coerceIn(0, 1) }
    }

    fun setMobileNumber(value: String) {
        val digits = value.filter { it.isDigit() }.take(9)
        val withoutLeadingZeros = digits.dropWhile { it == '0' }
        _mobileNumber.update { withoutLeadingZeros }
    }

    fun setPassword(value: String) {
        _password.update { value }
    }

    fun setSubmit(value: Boolean) {
        _submitButtonPressed.update { value }
    }

    fun togglePasswordVisible() {
        _passwordVisible.update { !it }
    }

    fun onLoginClick(onSuccess: () -> Unit) {
        _submitButtonPressed.update { true }
        val result = AuthValidation.validate(
            listOf(
                _mobileNumber.value to listOf(
                    ValidationRules.required(stringProvider.getString(R.string.please_input_your_mobile_number)),
                    ValidationRules.custom(stringProvider.getString(R.string.phone_number_must_be_9_digits)) { it.length == 9 },
                    ValidationRules.custom(stringProvider.getString(R.string.mobile_number_should_start_from_5)) { it.isNotEmpty() && it.first() == '5' }
                ),
                _password.value to listOf(
                    ValidationRules.required(stringProvider.getString(R.string.please_enter_password)),
                    ValidationRules.passwordStrength(stringProvider.getString(R.string.password_must_be_at_least_six_characters))
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
                ValidationShakeState(it.triggerId + 1, setOf(result.invalidIndices.first()))
            }
        } else {
            viewModelScope.launch {
                _isLoading.update { true }
                repository.loginWithPhone(
                    phoneNumber = _mobileNumber.value,
                    password = _password.value,
                    otp = null,
                    isPasswordSignIn = true
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
}