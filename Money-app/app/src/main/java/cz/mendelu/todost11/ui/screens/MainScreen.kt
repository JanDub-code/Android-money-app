package cz.mendelu.todost11.ui.screens

import android.graphics.Typeface
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartData
import cz.mendelu.todost11.R
import cz.mendelu.todost11.navigation.INavigationRouter
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import cz.mendelu.todost11.model.Transaction
import cz.mendelu.todost11.ui.elements.Drawer
import co.yml.charts.ui.piechart.models.PieChartConfig

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(navigationRouter: INavigationRouter,navController: NavHostController = rememberNavController()){

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
        id = R.string.main_title
    ))
    {
        TaskListScreenContent(
            paddingValues = it,
            transactions = transactions ,
            actions = viewModel,
            navigationRouter = navigationRouter,
            settingsData = settingsData

        )

    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBarSettingsMain(navigationRouter: INavigationRouter) {
    TopAppBar(
        title = {
            Text(text = stringResource(R.string.main_title))
        },
        actions = {
            IconButton(onClick = {
                navigationRouter.navigateToStatistics(null) // Metoda, která přesune uživatele na stránku statistik
            }) {
                Icon(Icons.Filled.Info, contentDescription = "Statistics") // Ikona informací
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF8BC34A)
        )

    )
}

fun calculateCurrentBalance(transactions: List<Transaction>): Long {
    var balance: Long = 0
    transactions.forEach { transaction ->
        when (transaction.type) {
            "Vklad","Deposit" -> balance += transaction.price
            "Výběr", "Platba","Payment","Withdraw" -> balance -= transaction.price
        }
    }
    return balance
}




@Composable
fun TaskListScreenContent(
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
                            painter = painterResource(id = R.drawable.wallet),
                            contentDescription = "Wallet Icon",
                            tint = Color.Black, // Barva ikony
                            modifier = Modifier.padding(end = 8.dp)
                        )

                        Text(
                            text = " " + stringResource(id = R.string.currentbalance) + " " + calculateCurrentBalance(transactions).toString(),
                            color = Color.Black,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }  }
        item { PieChartGraph(transactions = transactions) }
        item { Row(
            modifier = Modifier
                .fillMaxWidth()
                //.background(Color(0xFF8BC34A)) // Zelené pozadí pouze pro tuto sekci
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

        }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Button(
                    onClick = { navigationRouter.navigateToAddEditTask(null) },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFF8BC34A))
                ) {
                    Text(text = stringResource(id = R.string.paytypepurchase), color = Color.Black)
                }

                Button(
                    onClick = { navigationRouter.navigatetoWithdraw() },
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFF8BC34A))
                ) {
                    Text(text = stringResource(id = R.string.withdraw), color = Color.Black)
                }

                Button(
                    onClick = {navigationRouter.navigatetoDeposit() },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFF8BC34A))
                ) {
                    Text(text = stringResource(id = R.string.deposit), color = Color.Black)
                }
            }}


/*
        item {
            AsyncImage(
                model = "https://cataas.com/cat",
                contentDescription = "Placeholder Image",
                modifier = Modifier
                    .size(300.dp)
                    .aspectRatio(1f) // Maintain aspect ratio
            )

        }
        item {
            AsyncImage(
                model = "https://cdn.gobankingrates.com/wp-content/uploads/2020/04/TD-Bank-shutterstock_1091238983.jpg?webp=1&w=1280&quality=75",
                contentDescription = "Placeholder Image",
                modifier = Modifier
                    .size(300.dp)
                    .aspectRatio(1f) // Maintain aspect ratio
            )

        }*/


    }

}




@Composable
fun TaskListRow(transaction: Transaction,
                onClick:()->Unit,
                actions: MainActions) {
    Transaction(price = 0L,)
    //RecordItem(placeFrom = transaction.type.toString(), placeTo = transaction.detail, numberOfKm = transaction.price.toString())



}

fun pie(): PieChartData {
    return PieChartData(
        slices = listOf(
            PieChartData.Slice("ok", 45f, Color(0xFF333333))
        ),
        plotType = PlotType.Pie
    )
}

@ExperimentalCoilApi
@Composable
fun CoilImage(){
    Box(modifier = Modifier
        .height(300.dp)
        .width(300.dp)
    ){
        val painter = rememberImagePainter(data = "https://cataas.com/cat",
            builder = {
                placeholder(R.drawable.sync)
                crossfade(1000)
            })
        val painterState = painter.state
        Image(painter = painter, contentDescription = "logo")
    }
}

@Composable
fun PieChartGraph(transactions: List<Transaction>) {
    val countVklad = transactions.count { it.type == stringResource(id = R.string.deposit) || it.type == "Vklad"|| it.type == "Deposit" }
    val countPlatba = transactions.count { it.type == stringResource(id = R.string.paytypepurchase) || it.type == "Platba" || it.type == "Payment"}
    val countVyběr = transactions.count { it.type == stringResource(id = R.string.withdraw) || it.type == "Výběr" || it.type == "Withdraw"}
    if(transactions.isNotEmpty()) {
        val chartData: MutableList<PieChartData.Slice> = mutableListOf()

        chartData += PieChartData.Slice(
            label = stringResource(id = R.string.deposit),
            value = countVklad.toFloat(),
            color = Color.DarkGray,
        )

        chartData += PieChartData.Slice(
            label = stringResource(id = R.string.paytypepurchase),
            value = countPlatba.toFloat(),
            color = Color.Blue,
        )

        chartData += PieChartData.Slice(
            label = stringResource(id = R.string.withdraw),
            value = countVyběr.toFloat(),
            color = Color.Black
        )

        val pieChartData = PieChartData(
            slices = chartData,
            plotType = PlotType.Pie
        )
        val pieChartConfig = PieChartConfig(
            isAnimationEnable = true,
            showSliceLabels = true,
            activeSliceAlpha = 1f,
            animationDuration = 250,
            labelColor = Color.Black,
            strokeWidth = 2f,
            labelFontSize = 18.sp,
            labelTypeface = Typeface.DEFAULT_BOLD,
            labelVisible = true,
            labelType = PieChartConfig.LabelType.VALUE,
            labelColorType = PieChartConfig.LabelColorType.SPECIFIED_COLOR,
            backgroundColor = Color.White,
        )

        if(chartData.size != 0)
            PieChart(
                modifier = Modifier
                    .width(300.dp)
                    .height(300.dp),
                pieChartData = pieChartData,
                pieChartConfig = pieChartConfig
            )
    }
}

