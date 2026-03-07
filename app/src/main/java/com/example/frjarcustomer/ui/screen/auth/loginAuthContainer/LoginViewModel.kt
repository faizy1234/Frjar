package com.example.frjarcustomer.ui.screen.auth.loginAuthContainer

import androidx.lifecycle.ViewModel
import com.example.frjarcustomer.appstate.MessageContent
import com.example.frjarcustomer.appstate.MessageType
import com.example.frjarcustomer.appstate.SnackbarController
import com.example.frjarcustomer.appstate.SnackbarModel
import com.example.frjarcustomer.ui.components.AuthValidation
import com.example.frjarcustomer.ui.components.ValidationRules
import com.example.frjarcustomer.ui.components.ValidationShakeState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _pagerPage = MutableStateFlow(0)
    val pagerPage: StateFlow<Int> = _pagerPage.asStateFlow()

    private val _mobileNumber = MutableStateFlow("")
    val mobileNumber: StateFlow<String> = _mobileNumber.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _passwordVisible = MutableStateFlow(false)
    val passwordVisible: StateFlow<Boolean> = _passwordVisible.asStateFlow()

    private val _submitButtonPressed = MutableStateFlow(false)
    val submitButtonPressed: StateFlow<Boolean> = _submitButtonPressed.asStateFlow()

    private val _validationShake = MutableStateFlow(ValidationShakeState())
    val validationShake: StateFlow<ValidationShakeState> = _validationShake.asStateFlow()

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
                    ValidationRules.required("Mobile number is required"),
                    ValidationRules.custom("Must be 9 digits") { it.length == 9 },
                    ValidationRules.custom("This number must start with 5") { it.isNotEmpty() && it.first() == '5' }
                ),
                _password.value to listOf(
                    ValidationRules.required("Password is required"),
                    ValidationRules.passwordStrength()
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
            onSuccess()
        }
    }
}