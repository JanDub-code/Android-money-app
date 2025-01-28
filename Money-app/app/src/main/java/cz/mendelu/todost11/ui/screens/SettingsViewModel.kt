package cz.mendelu.todost11.ui.screens

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.mendelu.todost11.database.IBankRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import kotlin.math.floor

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository: IBankRepository) : ViewModel(),SettingsActions{

    private val _settingsUIState: MutableStateFlow<SettingsUIState> = MutableStateFlow(SettingsUIState.Loading())

    private var data: SettingsData = SettingsData()

    val settingsUIState = _settingsUIState.asStateFlow()


    init {
        viewModelScope.launch {
            repository.insertSettings(data.userSettings)
        }

    }

    fun loadSettings() {
        viewModelScope.launch {
            repository.getAllSettings().collect {
                data.userSettings = it[0]

                _settingsUIState.update {
                    SettingsUIState.ScreenDataChanged(data)
                }
            }

            _settingsUIState.update {
                SettingsUIState.ScreenDataChanged(data)
            }

        }
    }




    override fun saveSettings() {
        viewModelScope.launch {
            repository.updateSettings(data.userSettings)

            _settingsUIState.update {
                SettingsUIState.ScreenDataChanged(data)
            }
        }
    }

    override fun payLimitChanged(limit: String) {
        var payLimit = limit
        try {
            payLimit.toLong()

        } catch(e: NumberFormatException) {
            payLimit = data.userSettings.paylimit.toString()
        }

        data.userSettings.paylimit = payLimit.toLong()
        _settingsUIState.update {
            SettingsUIState.ScreenDataChanged(data)
        }
    }

    override fun withdrawlimitChanged(limit: String) {
        var with = limit
        try {
            with.toLong()
        }catch(e: NumberFormatException) {
            with = data.userSettings.withdrawalLimit.toString()
        }
        data.userSettings.withdrawalLimit = with.toLong()
        _settingsUIState.update { SettingsUIState.ScreenDataChanged(data) }
    }

    override fun PhotoChanged(photoUri: Uri?, context: Context) {
        if(photoUri.toString() != "") {
            copyImageToInternalStorage(context, photoUri!!) {}
        } else {
            data.userSettings.photo = ""
            _settingsUIState.update {
                SettingsUIState.ScreenDataChanged(data)
            }
            viewModelScope.launch {
                repository.setPhoto(data.userSettings.photo!!)
            }
        }
    }

    private fun copyImage(context: Context, uri: Uri): String? {
        val contentResolver: ContentResolver = context.contentResolver
        val fileName = floor(Math.random() * 100000000000).toString() + ".jpg" // You can generate a unique file name if needed
        val file = File(context.filesDir, fileName)

        try {
            contentResolver.openInputStream(uri).use { inputStream ->
                FileOutputStream(file).use { outputStream ->
                    val buffer = ByteArray(1024)
                    var length: Int
                    while (inputStream!!.read(buffer).also { length = it } > 0) {
                        outputStream.write(buffer, 0, length)
                    }
                }
            }
            return file.absolutePath
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }
    private fun copyImageToInternalStorage(context: Context, uri: Uri, onComplete: (String?) -> Unit) {
        viewModelScope.launch {
            val path = withContext(Dispatchers.IO) {
                copyImage(context, uri)
            }
            onComplete(path)

            data.userSettings.photo = path
            _settingsUIState.update {
                SettingsUIState.ScreenDataChanged(data)
            }
            viewModelScope.launch {
                repository.setPhoto(data.userSettings.photo!!)
            }
        }
    }




}