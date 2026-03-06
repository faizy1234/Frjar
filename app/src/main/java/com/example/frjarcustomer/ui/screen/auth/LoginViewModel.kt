package com.example.frjarcustomer.ui.screen.auth

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _selectedTabIndex = MutableStateFlow(0)
    val selectedTabIndex: StateFlow<Int> = _selectedTabIndex.asStateFlow()

    private val _mobileNumber = MutableStateFlow("")
    val mobileNumber: StateFlow<String> = _mobileNumber.asStateFlow()

    fun selectTab(index: Int) {
        _selectedTabIndex.update { index }
    }

    fun setMobileNumber(value: String) {
        _mobileNumber.update { value.filter { it.isDigit() } }
    }

    fun onContinue(
        moveToOtp: (String) -> Unit,
        moveToSignUpOtp: (String) -> Unit
    ) {
        when (_selectedTabIndex.value) {
            0 -> moveToOtp(_mobileNumber.value)
            1 -> moveToSignUpOtp(_mobileNumber.value)
            else -> moveToOtp(_mobileNumber.value)
        }
    }
}
