package cz.mendelu.todost11.ui.screens.transactions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.mendelu.todost11.database.IBankRepository
import cz.mendelu.todost11.utils.dateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import cz.mendelu.todost11.R
import cz.mendelu.todost11.ui.screens.AddTransactionPaymentScreenActions
import cz.mendelu.todost11.ui.screens.AddTransactionPaymentScreenData
import cz.mendelu.todost11.ui.screens.AddTransactionPaymentUIState
import cz.mendelu.todost11.ui.screens.SettingsData


@HiltViewModel
class DepositViewModel @Inject constructor(
    private val repository: IBankRepository
) : ViewModel(), AddTransactionPaymentScreenActions {

    private var data: AddTransactionPaymentScreenData = AddTransactionPaymentScreenData()

    private val _addTransactionPaymentUiState: MutableStateFlow<AddTransactionPaymentUIState> =
        MutableStateFlow(value = AddTransactionPaymentUIState.Loading())

    val addEditTaskUIState = _addTransactionPaymentUiState.asStateFlow()

    private val _settingsData = MutableStateFlow(SettingsData())
    val settingsData: StateFlow<SettingsData> = _settingsData


    init {
        loadSettings()
    }

    fun loadSettings() {
        viewModelScope.launch {
            repository.getAllSettings().collect {
                _settingsData.value.userSettings = it[0]
            }
        }
    }

    override fun saveTransaction() {
        data.priceError = null
        data.recipientTypeError = null

        if (data.transaction.price < 1L) {
            data.priceError = "musí být více než koruna"
        }

        if (data.transaction.recipient.isEmpty()) {
            data.recipientTypeError = "příjemnce nemůže být prázdný"
        }

        if (data.priceError != null || data.recipientTypeError != null) {
            _addTransactionPaymentUiState.update { AddTransactionPaymentUIState.ScreenDataChanged(data) }
        } else {
            // If no errors, save the transaction
            viewModelScope.launch {
                repository.insertTransaction(data.transaction)
            }
            _addTransactionPaymentUiState.update { AddTransactionPaymentUIState.TransactionPaymentSaved() }
        }
    }

    override fun priceChanged(text: String) {
        data.transaction.price = text.toLong()
        _addTransactionPaymentUiState.update { AddTransactionPaymentUIState.ScreenDataChanged(data) }
    }

    override fun dateChanged(text: Long) {
        data.transaction.date = text
        _addTransactionPaymentUiState.update { AddTransactionPaymentUIState.ScreenDataChanged(data) }
    }


    override fun recipientChanged(text: String) {
        data.transaction.recipient = text
        _addTransactionPaymentUiState.update { AddTransactionPaymentUIState.ScreenDataChanged(data) }
    }

    override fun detailChanged(text: String) {
        data.transaction.detail = text
        _addTransactionPaymentUiState.update { AddTransactionPaymentUIState.ScreenDataChanged(data) }
    }

    override fun deleteTransaction(id: Long) {
        TODO("Not yet implemented")
    }


}