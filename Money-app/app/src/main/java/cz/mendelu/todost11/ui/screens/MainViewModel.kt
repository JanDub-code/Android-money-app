package cz.mendelu.todost11.ui.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.mendelu.todost11.database.IBankRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: IBankRepository) : ViewModel() ,MainActions{
    val mainUIState: MutableState<MainUIState> = mutableStateOf(MainUIState.Loading())
    private var data: SettingsData = SettingsData()

    private val _settingsData = MutableStateFlow(SettingsData())
    val settingsData: StateFlow<SettingsData> = _settingsData


    init {
        viewModelScope.launch {
            repository.insertSettings(data.userSettings)
        }
        loadSettings()
    }

    fun loadSettings() {
        viewModelScope.launch {
            repository.getAllSettings().collect {
                _settingsData.value.userSettings = it[0]
            }
        }
    }

    fun loadTasks(){
        viewModelScope.launch {
            repository.getAllTransactions().collect {
                mainUIState.value = MainUIState.Success(it)
            }
        }

    }

}



