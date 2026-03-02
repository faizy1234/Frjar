package com.example.frjarcustomer.appstate
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frjarcustomer.core.di.LanguageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainActivityVm @Inject constructor(
    private val repository: LanguageRepository
) : ViewModel() {

    val currentLanguage: StateFlow<AppLanguage> = repository.currentLanguage
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = AppLanguage.DEFAULT
        )


}
