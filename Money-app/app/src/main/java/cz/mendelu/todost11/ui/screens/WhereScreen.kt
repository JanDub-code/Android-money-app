package cz.mendelu.todost11.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cz.mendelu.todost11.R
import cz.mendelu.todost11.navigation.INavigationRouter
import coil.compose.AsyncImage
import cz.mendelu.todost11.model.Transaction
import cz.mendelu.todost11.ui.elements.Drawer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WhereScreen(navigationRouter: INavigationRouter,navController: NavHostController = rememberNavController()){

    val viewModel = hiltViewModel<MainViewModel>()
    val settingsData by viewModel.settingsData.collectAsState()
    val transactions: MutableList<Transaction> = mutableListOf()

    viewModel.mainUIState.value.let {
        when(it){
            is MainUIState.Loading -> {
                viewModel.loadTasks()
            }
            is MainUIState.Success -> {
                transactions.addAll(it.transaction)
            }
        }

    }
    Drawer(navigationRouter = navigationRouter, navController = navController, text = stringResource(
        id = R.string.where
    ))
    {
        WhereScreenContent(
            paddingValues = it,
            transactions = transactions ,
            actions = viewModel,
            navigationRouter = navigationRouter,
            settingsData = settingsData

        )

    }

}
@Composable
fun WhereScreenContent(
    paddingValues: PaddingValues,
    transactions: List<Transaction>,
    actions: MainActions,
    navigationRouter : INavigationRouter,
    settingsData: SettingsData
){

    LazyColumn (
        modifier = Modifier.padding(paddingValues),    horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {Spacer(modifier = Modifier.padding(top = 8.dp))
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),

                color = Color(0xFF8BC34A)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.where),
                            contentDescription = "Wallet Icon",
                            tint = Color.Black, // Barva ikony
                            modifier = Modifier.padding(end = 8.dp)
                        )

                        Text(
                            text = stringResource(id = R.string.wherebranch) ,
                            color = Color.Black,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }

        item {
            AsyncImage(
                model = "https://cdn.gobankingrates.com/wp-content/uploads/2020/04/TD-Bank-shutterstock_1091238983.jpg?webp=1&w=1280&quality=75",
                contentDescription = "Placeholder Image",
                modifier = Modifier
                    .size(300.dp)
                    .aspectRatio(1f) // Maintain aspect ratio
            )

        }
        item { Text(
            text =  stringResource(id = R.string.whereinfo),            textAlign = TextAlign.Center, ) }



    }
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        Button(
            onClick = { navigationRouter.returnBack() },
            colors = ButtonDefaults.buttonColors(Color(0xFF8BC34A)),
            modifier = Modifier
                .padding(16.dp)
                .size(56.dp),
            shape = CircleShape,
            contentPadding = PaddingValues(0.dp),

            ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black // Barva ikony
            )
        }

    }
}
