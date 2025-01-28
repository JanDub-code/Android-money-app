package cz.mendelu.todost11.ui.screens

import cz.mendelu.todost11.model.UserSettings

sealed class SettingsUIState {
    class ScreenDataChanged(val data: SettingsData): SettingsUIState()
    class Loading : SettingsUIState()
    class Success(val settings: List<UserSettings>) : SettingsUIState()
    class Error(val errorMessage: String) : SettingsUIState()


}