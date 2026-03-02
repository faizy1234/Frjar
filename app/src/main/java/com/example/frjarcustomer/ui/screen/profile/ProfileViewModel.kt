package com.example.frjarcustomer.ui.screen.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val userId: String = savedStateHandle.get<String>("userId") ?: ""
    val tab: Int = savedStateHandle.get<Int>("tab") ?: 0
}
