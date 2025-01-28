package cz.mendelu.todost11.navigation

import androidx.navigation.NavController

class NavigationRouterImpl(private val navController: NavController) : INavigationRouter {

    override fun navigateToAddEditTask(id: Long?) {
        if (id!=null){
        navController.navigate(Destination.AddEditTaskScreen.route+ "/" +id)
    }else {
        navController.navigate(Destination.AddEditTaskScreen.route)
        }
    }

    override fun navigateToEdit(id: Long?) {
        if (id!=null){
            navController.navigate(Destination.editTransaction.route+ "/" +id)
    }else {
            navController.navigate(Destination.editTransaction.route)
        }
    }

    override fun returnBack() {
        navController.popBackStack()
    }

    override fun getNavController(): NavController {
        return  navController
    }

    override fun navigateToStatistics(id: Long?) {
        navController.navigate(Destination.Statistics.route)
    }

    override fun navigateToMain() {
        navController.navigate(Destination.TaskListScreen.route)
    }

    override fun navigatetoDeposit() {
        navController.navigate(Destination.deposit.route)
    }

    override fun navigatetoWithdraw() {
        navController.navigate(Destination.withdraw.route)
    }

    override fun navigateToSettings(id: Long?) {
        navController.navigate(Destination.Settings.route)
    }

    override fun navigateToWhereScreen() {
        navController.navigate(Destination.whereScreen.route)
    }


}