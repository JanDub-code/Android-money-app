package cz.mendelu.todost11.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cz.mendelu.todost11.ui.screens.AddEditTaskScreen
import cz.mendelu.todost11.ui.screens.SettingsScreen
import cz.mendelu.todost11.ui.screens.StatisticsScreen
import cz.mendelu.todost11.ui.screens.TaskListScreen
import cz.mendelu.todost11.ui.screens.WhereScreen
import cz.mendelu.todost11.ui.screens.transactions.DepositScreen
import cz.mendelu.todost11.ui.screens.transactions.EditScreen
import cz.mendelu.todost11.ui.screens.transactions.WithdrawScreen

@Composable
fun NavGraph(
    navHostController: NavHostController = rememberNavController(),
    navigationRouter: INavigationRouter = remember {
        NavigationRouterImpl(navHostController)
    },
    startDestination: String
){
    
    NavHost(navController = navHostController, startDestination = startDestination) {

        composable(Destination.TaskListScreen.route){
            TaskListScreen(navigationRouter = navigationRouter)
        }

        composable(Destination.AddEditTaskScreen.route+ "/{id}", arguments = listOf(navArgument("id"){
            type= NavType.LongType
            defaultValue = -1L
        })){
            val id = it.arguments?.getLong("id")
            AddEditTaskScreen(navigationRouter = navigationRouter,
                id=id)
        }

        composable(Destination.AddEditTaskScreen.route){
            AddEditTaskScreen(navigationRouter = navigationRouter,id=null)
        }

        composable(Destination.Statistics.route){
            StatisticsScreen(navigationRouter = navigationRouter)
        }
        
        composable(Destination.Settings.route){
            SettingsScreen(navigationRouter = navigationRouter)
        }

        composable(Destination.withdraw.route){
            WithdrawScreen(navigationRouter = navigationRouter)
        }
        composable(Destination.deposit.route){
            DepositScreen(navigationRouter = navigationRouter)
        }

        composable(Destination.editTransaction.route+ "/{id}", arguments = listOf(navArgument("id"){
            type= NavType.LongType
            defaultValue = -1L
        })){
            val id = it.arguments?.getLong("id")
            EditScreen(navigationRouter = navigationRouter, id = id)
        }

        composable(Destination.whereScreen.route){
            WhereScreen(navigationRouter = navigationRouter)
        }


    }
    
}
