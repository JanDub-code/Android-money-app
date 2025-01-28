package cz.mendelu.todost11.ui.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.mendelu.todost11.database.IBankRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(private val repository: IBankRepository) : ViewModel() ,StatisticsActions{

    val statisticsUIState: MutableState<StatisticsUIState> = mutableStateOf(StatisticsUIState.Loading())

    

    fun loadTasks(){
        viewModelScope.launch {
            repository.getAllTransactions().collect {
                statisticsUIState.value = StatisticsUIState.Success(it)
            }
        }

    }


}