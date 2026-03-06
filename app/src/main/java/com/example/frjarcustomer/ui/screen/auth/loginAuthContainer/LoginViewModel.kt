package com.example.frjarcustomer.ui.screen.auth.loginAuthContainer

import androidx.lifecycle.ViewModel
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

    private val _emailOrMobile = MutableStateFlow("")
    val emailOrMobile: StateFlow<String> = _emailOrMobile.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _passwordVisible = MutableStateFlow(false)
    val passwordVisible: StateFlow<Boolean> = _passwordVisible.asStateFlow()

    private val _submitButtonPressed = MutableStateFlow(false)
    val submitButtonPressed: StateFlow<Boolean> = _submitButtonPressed.asStateFlow()

    fun setPagerPage(page: Int) {
        _pagerPage.update { page.coerceIn(0, 1) }
    }

    fun setEmailOrMobile(value: String) {
        _emailOrMobile.update { value }
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
}