package cz.mendelu.todost11.ui.screens.transactions

interface EditActions {
    fun saveTransaction()
    fun priceChanged(text: String)
    fun dateChanged(text: Long)
    fun recipientChanged(text: String)
    fun detailChanged(text: String)
    fun deleteTransaction(id : Long)

    fun updateTransaction()
}