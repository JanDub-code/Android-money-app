package cz.mendelu.todost11

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cz.mendelu.todost11.navigation.Destination
import cz.mendelu.todost11.navigation.NavGraph
import cz.mendelu.todost11.ui.theme.ToDoSt11Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoSt11Theme {
                NavGraph(startDestination = Destination.TaskListScreen.route)
            }
        }
    }
}













