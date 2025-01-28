package cz.mendelu.todost11.ui.screens.transactions

import cz.mendelu.todost11.ui.screens.AddTransactionPaymentScreenActions
import cz.mendelu.todost11.ui.screens.AddTransactionPaymentScreenData
import cz.mendelu.todost11.ui.screens.AddTransactionPaymentUIState
import cz.mendelu.todost11.ui.screens.SettingsData
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


@HiltViewModel
class EditViewModel @Inject constructor(
    private val repository: IBankRepository
) : ViewModel(), EditActions {

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
    /*
        override fun saveTask() {
            // Check each input field for errors
            data.priceError = if (data.record.from.isEmpty()) {
                "Location from cannot be empty"
            } else {
                null
            }

            data.dateError = if (data.record.to.isEmpty()) {
                "Location to cannot be empty"
            } else {
                null
            }

            data.numberOfKmError = if (data.record.numberOfKm <= 0) {
                "Number of kilometers cannot be 0"
            } else {
                null
            }

            data.priceError = if (data.record.priceOfFuel <= 0) {
                "Price of fuel cannot be 0"
            } else {
                null
            }

            data.fuelTypeError = if (data.record.fuelType.isEmpty()) {
                "Fuel type cannot be empty"
            } else {
                null
            }

            // Other error checks for different fields...

            // Update UI state with specific error messages
            if (data.priceError != null || data.toError != null || data.numberOfKmError != null || data.priceError != null || data.fuelTypeError != null) {
                _addTransactionPaymentUiState.update { AddTransactionPaymentUIState.ScreenDataChanged(data) }
            } else {
                // If no errors, proceed with saving the task
                viewModelScope.launch {
                    if (data.record.id == null) {
                        repository.insert(data.record)
                        _addTransactionPaymentUiState.update { AddTransactionPaymentUIState.TransactionPaymentSaved() }
                    } else {
                        // Handle edit case if needed
                    }
                }
            }
        }

     */
    override fun saveTransaction() {
        data.priceError = null
        data.recipientTypeError = null

        if (data.transaction.price < 1L) {
            data.priceError = "musí být více než koruna"
        } else if (data.transaction.price > settingsData.value.userSettings.paylimit) {
            data.priceError = "nelze zaplatit více než limit"
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

    override fun updateTransaction(){
        viewModelScope.launch {
            repository.updateTransaction(data.transaction)
            _addTransactionPaymentUiState.update {
                AddTransactionPaymentUIState.TransactionDeleted()
            }
        }
    }

    override fun deleteTransaction(id: Long) {
        viewModelScope.launch {
            repository.deleteTransaction(id)
            _addTransactionPaymentUiState.update {
                AddTransactionPaymentUIState.TransactionDeleted()
            }
        }
    }
    fun loadTransaction(id: Long) {
        viewModelScope.launch {
            data.transaction= repository.getTransaction(id)
            _addTransactionPaymentUiState.update {
                AddTransactionPaymentUIState.ScreenDataChanged(data)
            }
        }
        if (id != null){
            viewModelScope.launch { data.transaction = repository.getTransaction(id)
                _addTransactionPaymentUiState.update { AddTransactionPaymentUIState.ScreenDataChanged(data) }
            }
        }
    }
    /*
        fun loadPurchase(id: Long) {
            viewModelScope.launch {
                repository.getTransaction(id).collect {
                    data.transaction = it
                }
                _addEditPurchaseUIState.update {
                    AddEditPurchaseListUIState.ScreenDataChanged(data)
                }
            }

            if(id != null) {
                viewModelScope.launch {
                    data.purchase = repository.getPurchase(id)
                    if(data.purchase.storeId != null) {
                        data.storeById = repository.getStoreById(data.purchase.storeId!!)
                    }
                    _addEditPurchaseUIState.update {
                        AddEditPurchaseListUIState.ScreenDataChanged(data)
                    }
                }
            }
        }*/


}