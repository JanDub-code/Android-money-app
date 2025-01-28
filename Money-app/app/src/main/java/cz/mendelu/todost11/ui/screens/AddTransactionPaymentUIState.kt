package cz.mendelu.todost11.ui.screens

sealed class AddTransactionPaymentUIState {
    class Loading : AddTransactionPaymentUIState()
    class TransactionPaymentSaved : AddTransactionPaymentUIState()
    class ScreenDataChanged(val data:AddTransactionPaymentScreenData) : AddTransactionPaymentUIState()

    class TransactionPaymentDeleted : AddTransactionPaymentUIState()
    class TransactionDeleted :AddTransactionPaymentUIState()
}