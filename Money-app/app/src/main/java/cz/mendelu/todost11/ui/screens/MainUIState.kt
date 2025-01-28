package cz.mendelu.todost11.ui.screens

import cz.mendelu.todost11.model.Transaction

sealed class MainUIState {
    class Loading : MainUIState()
    class Success(val transaction: List<Transaction>) : MainUIState()
}