package com.example.frjarcustomer.ui.screen.language

import androidx.annotation.DrawableRes
import com.example.frjarcustomer.R
import com.example.frjarcustomer.appstate.AppLanguage
import com.example.frjarcustomer.core.di.LanguageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

data class LanguageItemState(
    val language: AppLanguage,
    @DrawableRes val flagResId: Int,
    val enabled: Boolean = true
)

@HiltViewModel
class LanguageSelectionViewModel @Inject constructor(
    private val languageRepository: LanguageRepository
) : ViewModel() {

    val currentLanguage: StateFlow<AppLanguage> = languageRepository.currentLanguage

    private val _selectedLanguage = MutableStateFlow<AppLanguage>(AppLanguage.DEFAULT)
    val selectedLanguage: StateFlow<AppLanguage> = _selectedLanguage.asStateFlow()

    val suggestedList: List<LanguageItemState> = AppLanguage.SUPPORTED.take(2).map { lang ->
        LanguageItemState(lang, getFlagResId(lang), true)
    }

    val otherList: List<LanguageItemState> = AppLanguage.SUPPORTED.drop(2).map { lang ->
        LanguageItemState(lang, getFlagResId(lang), true)
    }

    init {
        viewModelScope.launch {
            languageRepository.currentLanguage.collect { _selectedLanguage.value = it }
        }
    }

    fun setSelected(language: AppLanguage) {
        _selectedLanguage.value = language
    }

    suspend fun save() {
        languageRepository.setLanguage(_selectedLanguage.value)
    }

    private fun getFlagResId(lang: AppLanguage): Int = when (lang.languageCode) {
        com.example.frjarcustomer.data.remote.utils.AppLanguage.ENGLISH.value -> R.drawable.ic_us
        com.example.frjarcustomer.data.remote.utils.AppLanguage.ARABIC.value -> R.drawable.ic_flag
        com.example.frjarcustomer.data.remote.utils.AppLanguage.URDU.value -> R.drawable.ic_flag_ur
        com.example.frjarcustomer.data.remote.utils.AppLanguage.HINDI.value-> R.drawable.ic_in
        else -> R.drawable.ic_language
    }
}
