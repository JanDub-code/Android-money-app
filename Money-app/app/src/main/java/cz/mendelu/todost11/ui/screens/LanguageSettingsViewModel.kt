package cz.mendelu.todost11.ui.screens

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.mendelu.todost11.utils.LocaleManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LanguageSettingsViewModel : ViewModel() {

    private val _currentLanguage = MutableStateFlow("cs")
    val currentLanguage: StateFlow<String> = _currentLanguage

    fun setLanguage(languageCode: String, context: Context) {
        viewModelScope.launch {
            _currentLanguage.value = languageCode
            LocaleManager.updateLocale(context, languageCode)
        }
    }
}