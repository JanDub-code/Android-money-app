package cz.mendelu.todost11.ui.screens

import android.content.Context
import android.net.Uri

interface SettingsActions {
    fun saveSettings()

    fun payLimitChanged(limit: String)

    fun withdrawlimitChanged(limit: String)

    fun PhotoChanged(photoUri: Uri?, context: Context)
}