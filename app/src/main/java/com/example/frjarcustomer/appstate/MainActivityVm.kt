package com.example.frjarcustomer.appstate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frjarcustomer.core.di.LanguageRepository
import com.example.frjarcustomer.core.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ProfileTabEvent {
    data object NavigateToAuth : ProfileTabEvent()
    data object NavigateToProfile : ProfileTabEvent()
}

@HiltViewModel
class MainActivityVm @Inject constructor(
    private val repository: LanguageRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    val currentLanguage: StateFlow<AppLanguage> = repository.currentLanguage
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = AppLanguage.DEFAULT
        )

    private val _profileTabEvent = Channel<ProfileTabEvent>(Channel.BUFFERED)
    val profileTabEvent = _profileTabEvent.receiveAsFlow()

    fun onProfileTabClicked() {
        viewModelScope.launch {
            val user = sessionManager.getUser()
            if (user == null || user.userId.isNullOrEmpty()) {
                _profileTabEvent.send(ProfileTabEvent.NavigateToAuth)
            } else {
                _profileTabEvent.send(ProfileTabEvent.NavigateToProfile)
            }
        }
    }
}
