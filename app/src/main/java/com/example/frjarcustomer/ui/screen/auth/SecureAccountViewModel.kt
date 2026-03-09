package com.example.frjarcustomer.ui.screen.auth

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
import com.example.frjarcustomer.ui.components.AuthValidation
import com.example.frjarcustomer.ui.components.ValidationRules
import com.example.frjarcustomer.ui.components.ValidationShakeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SecureAccountViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: Repository,
    private val stringProvider: StringProvider

) : ViewModel() {

    val phoneNumber = savedStateHandle.toRoute<AppRoute.SecureAccount>().phoneNumber

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword.asStateFlow()

    private val _passwordVisible = MutableStateFlow(false)
    val passwordVisible: StateFlow<Boolean> = _passwordVisible.asStateFlow()

    private val _confirmPasswordVisible = MutableStateFlow(false)
    val confirmPasswordVisible: StateFlow<Boolean> = _confirmPasswordVisible.asStateFlow()

    private val _validationShake = MutableStateFlow(ValidationShakeState())
    val validationShake: StateFlow<ValidationShakeState> = _validationShake.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun setEmail(value: String) = _email.update { value }
    fun setPassword(value: String) = _password.update { value }
    fun setConfirmPassword(value: String) = _confirmPassword.update { value }
    fun togglePasswordVisible() = _passwordVisible.update { !it }
    fun toggleConfirmPasswordVisible() = _confirmPasswordVisible.update { !it }

    fun onNextClick(onSuccess: () -> Unit) {
        val result = AuthValidation.validate(
            listOf(
                _email.value to listOf(
                    ValidationRules.required(stringProvider.getString(R.string.please_enter_email_address)),
                    ValidationRules.email(stringProvider.getString(R.string.please_enter_valid_email_address))
                ),
                _password.value to listOf(
                    ValidationRules.required(stringProvider.getString(R.string.please_enter_password)),
                    ValidationRules.passwordStrength(stringProvider.getString(R.string.password_must_be_at_least_six_characters))
                ),
                _confirmPassword.value to listOf(
                    ValidationRules.required(stringProvider.getString(R.string.please_enter_confirm_password)),
                    ValidationRules.passwordStrength(stringProvider.getString(R.string.confirm_password_must_be_at_least_six_characters)),
                    ValidationRules.match(_password.value, stringProvider.getString(R.string.passwords_do_not_match))
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
            val shakeIndices = when (val firstInvalid = result.invalidIndices.first()) {
                0 -> setOf(0)
                1 -> setOf(1)
                2 -> if (_confirmPassword.value.isNotEmpty() && _confirmPassword.value != _password.value)
                    setOf(1, 2)
                else
                    setOf(2)
                else -> setOf(firstInvalid)
            }
            _validationShake.update {
                ValidationShakeState(it.triggerId + 1, shakeIndices)
            }
        } else {

            onSuccess()

//            viewModelScope.launch {
//                _isLoading.update { true }
//                repository.userRegister(
//                    email = _email.value,
//                    phoneNumber = phoneNumber,
//                    password = _password.value
//                ).collect { apiResult ->
//                    when (apiResult) {
//                        is ApiResult.Loading -> { }
//                        is ApiResult.Success -> {
//                            _isLoading.update { false }
//                            onSuccess()
//                        }
//                        is ApiResult.Error -> {
//                            _isLoading.update { false }
//                            SnackbarController.show(
//                                SnackbarModel(
//                                    type = MessageType.ERROR,
//                                    message = MessageContent.PlainString(apiResult.message)
//                                )
//                            )
//                        }
//                    }
//                }
//            }
        }
    }
}
