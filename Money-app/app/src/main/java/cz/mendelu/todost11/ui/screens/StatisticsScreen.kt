package cz.mendelu.todost11.ui.screens

import android.graphics.Typeface
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import cz.mendelu.todost11.R
import cz.mendelu.todost11.model.Transaction
import cz.mendelu.todost11.navigation.INavigationRouter
import cz.mendelu.todost11.ui.elements.Drawer
import cz.mendelu.todost11.utils.dateUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(navigationRouter: INavigationRouter,navController: NavHostController = rememberNavController()) {
    val viewModel = hiltViewModel<StatisticsViewModel>()

    val transactions: MutableList<Transaction> = mutableListOf()

    viewModel.statisticsUIState.value.let {
        when (it) {
            is StatisticsUIState.Loading -> {
                viewModel.loadTasks()
            }
            is StatisticsUIState.Success -> {
                transactions.addAll(it.transaction)
            }
        }
    }

    Drawer(navigationRouter = navigationRouter, navController = navController, text = stringResource(
        id = R.string.statistic_title
    )
    )
    {
            StatisticsScreenContent(
                paddingValues = it,
                transaction = transactions,
                actions = viewModel,
                navigationRouter = navigationRouter,
            )
        }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(navigationRouter: INavigationRouter) {
    TopAppBar(
        title = {
            Text(text = "Your statistics")
        },
        navigationIcon = {
            IconButton(onClick = {
                navigationRouter.returnBack()
            }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF8BC34A) // Světle zelená barva
        )
    )
}

@Composable
fun StatisticsScreenContent(
    paddingValues: PaddingValues,
    transaction: List<Transaction>,
    actions: StatisticsActions,
    navigationRouter: INavigationRouter,
) {
    LazyColumn(
        modifier = Modifier.padding(paddingValues)
    ) {
        // Horní zelená sekce
        item { Box(
            modifier = Modifier
                .fillMaxSize(), // Ensure the Box takes up the full size of the LazyColumn
            contentAlignment = Alignment.Center // Center the content of the Box
        ) { PieChartGraphStatistics(transactions = transaction)}}
        transaction.forEach {
            item {
                TransactionListRow(
                    transaction = it,
                    onClick = { navigationRouter.navigateToEdit(it.id) }
                )

                /*
                Text(text = it.type.toString())
                Text(text = it.detail)
                Text(text = it.recipient)
                Text(text = it.price.toString())
                /*TaskListRow(record = it, onClick = {
                               navigationRouter.navigateToAddEditTask(it.id)
                },actions=actions)*/*/
            }
        }

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

@Composable
fun PieChartGraphStatistics(transactions: List<Transaction>) {
    var expenses : Double =0.0
    var income : Double=0.0
    transactions.forEach { transaction ->
        when (transaction.type) {
            "Vklad","Deposit" -> income += transaction.price
            "Výběr", "Platba","Payment","Withdraw" -> expenses += transaction.price
        }
    }

    if(transactions.isNotEmpty()) {
        val chartData: MutableList<PieChartData.Slice> = mutableListOf()

        chartData += PieChartData.Slice(
            label = stringResource(id = R.string.income),
            value = income.toFloat(),
            color = Color.Blue,
        )

        chartData += PieChartData.Slice(
            label = stringResource(id = R.string.expenditure),
            value = expenses.toFloat(),
            color = Color.DarkGray,
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

        PieChart(
            modifier = Modifier
                .width(300.dp)
                .height(300.dp),
            pieChartData = pieChartData,
            pieChartConfig = pieChartConfig
        )
    }
}


@Composable
fun TransactionListRow(
    transaction: Transaction,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp)
            .background(color = Color(0xFFE0E0E0), shape = RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(5.dp))
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {


                    Icon(
                        painter = painterResource(id = R.drawable.price),
                        tint = Color.Black,
                        contentDescription = null
                    )
                Spacer(modifier = Modifier.width(5.dp))

                Text(text = stringResource(id = R.string.price) + ": "+ transaction.price)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.type),
                    tint = Color.Black,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(5.dp))

                Text(text = stringResource(id = R.string.type) + ": "+ transaction.type)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.recipient),
                    tint = Color.Black,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(5.dp))

                Text(text = stringResource(id = R.string.recipient) + ": "+ transaction.recipient)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.date),
                    tint = Color.Black,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(5.dp))

                Text(text = stringResource(id = R.string.date) + ": ")
                Text(text = dateUtils.getDateString(transaction.date!!))
            }
        }
    }
}
