package com.example.frjarcustomer.ui.screen.dashedboard.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    fun getWelcomeMessage(): String = "Welcome — tap an item to open Detail"
}
