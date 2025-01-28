package cz.mendelu.todost11.ui.screens

import cz.mendelu.todost11.model.Transaction

class AddTransactionPaymentScreenData {
    var transaction: Transaction= Transaction(0)
    var dateError: String? = null
    var typeError: String? = null
    var recipientTypeError: String? = null
    var priceError: String? = null
}