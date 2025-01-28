package cz.mendelu.todost11.ui.screens

import cz.mendelu.todost11.model.Transaction

sealed class StatisticsUIState {
    class Loading : StatisticsUIState()
    class Success(val transaction: List<Transaction>) : StatisticsUIState()

}