package cz.mendelu.todost11.navigation

sealed class Destination(val route: String){
    object TaskListScreen : Destination("task_list")
    object AddEditTaskScreen : Destination("add_edit_task")
    object Statistics : Destination("statistics")

    object Settings : Destination("settings")

    object withdraw : Destination("withdraw")
    object deposit : Destination("deposit")

    object editTransaction : Destination("edit")
    object whereScreen: Destination("where")
}