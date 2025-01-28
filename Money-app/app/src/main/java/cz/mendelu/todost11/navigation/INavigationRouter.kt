package cz.mendelu.todost11.navigation

import androidx.navigation.NavController

interface INavigationRouter {
    fun navigateToAddEditTask(id: Long?)

    fun navigateToEdit(id : Long?)
    fun returnBack()
    fun getNavController(): NavController
    fun navigateToStatistics(id : Long?)

    fun navigateToMain()

    fun navigatetoDeposit()
    fun navigatetoWithdraw()

    fun navigateToSettings(id : Long?)

    fun navigateToWhereScreen()
}