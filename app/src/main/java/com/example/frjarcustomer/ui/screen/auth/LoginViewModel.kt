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
import com.example.frjarcustomer.core.location.LocationStoreHelper
import com.example.frjarcustomer.data.remote.repository.Repository
import com.example.frjarcustomer.data.remote.utils.ApiResult
import com.example.frjarcustomer.navigation.routes.AppRoute
import com.example.frjarcustomer.ui.components.AuthValidation
import com.example.frjarcustomer.ui.components.ValidationRules
import com.example.frjarcustomer.ui.components.ValidationShakeState
import com.example.frjarcustomer.utils.UserinfoManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: Repository,
    private val locationStoreHelper: LocationStoreHelper,
    private val stringProvider: StringProvider,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    val previousRouteTab = savedStateHandle.toRoute<AppRoute.Login>().initialTab
    private val _selectedTabIndex = MutableStateFlow(previousRouteTab)
    val selectedTabIndex: StateFlow<Int> = _selectedTabIndex.asStateFlow()

    private val _mobileNumber = MutableStateFlow("")
    val mobileNumber: StateFlow<String> = _mobileNumber.asStateFlow()

    private val _validationShake = MutableStateFlow(ValidationShakeState())
    val validationShake: StateFlow<ValidationShakeState> = _validationShake.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun selectTab(index: Int) {
        _selectedTabIndex.update { index }
    }

    fun setMobileNumber(value: String) {
        val digits = value.filter { it.isDigit() }.take(9)
        val withoutLeadingZeros = digits.dropWhile { it == '0' }
        _mobileNumber.update { withoutLeadingZeros }
    }

    fun onContinue(
        moveToOtp: (String) -> Unit,
        moveToSignUpOtp: (String) -> Unit
    ) {
        val result = AuthValidation.validate(
            listOf(
                _mobileNumber.value to listOf(
                    ValidationRules.required("Mobile number is Required"),
                    ValidationRules.custom(stringProvider.getString(R.string.phone_number_must_be_9_digits)) { it.length == 9 },

                    ValidationRules.custom(stringProvider.getString(R.string.mobile_number_should_start_from_5)) { it.isNotEmpty() && it.first() == '5' }
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
            when (_selectedTabIndex.value) {
                0 -> {
                    val phone = _mobileNumber.value
                    sendLoginOtp(phone = phone, moveToOtp = { moveToOtp(phone) })
                }

                1 -> {
                    val phone = _mobileNumber.value
                    registerWithPhoneApi(
                        phone = phone,
                        moveToSignUpOtp = { moveToSignUpOtp(phone) })
                }

                else -> moveToOtp(_mobileNumber.value)
            }
        }
    }

    private fun sendLoginOtp(
        moveToOtp: (String?) -> Unit,
        phone: String?,
    ) {
        viewModelScope.launch {
            _isLoading.update { true }
            repository.sendLoginOtp(phone).collect { apiResult ->
                when (apiResult) {
                    is ApiResult.Loading -> {

                    }

                    is ApiResult.Success -> {
                        UserinfoManager.init(apiResult.data.data?.userId)
                        _isLoading.update { false }
                        moveToOtp(phone)
                    }

                    is ApiResult.Error -> {
                        UserinfoManager.resetState()
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

    private fun registerWithPhoneApi(
        phone: String,
        moveToSignUpOtp: (String) -> Unit
    ) {
        viewModelScope.launch {
            _isLoading.update { true }
            repository.registerWithPhone(phone).collect { apiResult ->
                when (apiResult) {
                    is ApiResult.Loading -> {}
                    is ApiResult.Success -> {
                        UserinfoManager.init(apiResult.data.data?.userId)

                        _isLoading.update { false }
                        moveToSignUpOtp(phone)
                    }

                    is ApiResult.Error -> {
                        UserinfoManager.resetState()

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

    fun onLocationPermissionGranted() {
        viewModelScope.launch {
            locationStoreHelper.fetchAndSaveLocation()
        }
    }

    override fun onCleared() {
        super.onCleared()
        UserinfoManager.resetState()

    }


}
