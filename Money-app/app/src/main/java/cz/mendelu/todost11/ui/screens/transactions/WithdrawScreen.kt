package cz.mendelu.todost11.ui.screens.transactions


import android.graphics.drawable.Icon
import android.text.format.DateUtils
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cz.mendelu.todost11.R
import cz.mendelu.todost11.navigation.INavigationRouter
import cz.mendelu.todost11.ui.elements.CustomDatePickerDialog
import cz.mendelu.todost11.ui.elements.Drawer
import cz.mendelu.todost11.ui.elements.InfoElement
import cz.mendelu.todost11.ui.screens.AddTransactionPaymentScreenActions
import cz.mendelu.todost11.ui.screens.AddTransactionPaymentScreenData
import cz.mendelu.todost11.ui.screens.AddTransactionPaymentUIState
import cz.mendelu.todost11.ui.screens.AddTransactionPaymentViewModel
import cz.mendelu.todost11.ui.screens.SettingsData
import cz.mendelu.todost11.utils.dateUtils


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WithdrawScreen(navigationRouter: INavigationRouter,navController: NavHostController = rememberNavController()){

    val viewModel = hiltViewModel<WithdrawViewModel>()
    val settingsData by viewModel.settingsData.collectAsState()

    val state=viewModel.addEditTaskUIState.collectAsStateWithLifecycle()

    val actions = viewModel

    var data by remember {
        mutableStateOf(AddTransactionPaymentScreenData())
    }

    state.value.let {
        when(it){

            is AddTransactionPaymentUIState.TransactionPaymentSaved -> {
                LaunchedEffect(it) {
                    navigationRouter.returnBack()
                }
            }

            is AddTransactionPaymentUIState.ScreenDataChanged -> {
                data = it.data
            }

            is AddTransactionPaymentUIState.TransactionPaymentDeleted -> {
                LaunchedEffect(it) {
                    navigationRouter.returnBack()
                }
            }

            is AddTransactionPaymentUIState.Loading -> {

            }

            is AddTransactionPaymentUIState.TransactionDeleted -> {
                LaunchedEffect(it) {
                    navigationRouter.returnBack()
                }
            }
        }
    }


    Drawer(navigationRouter = navigationRouter, navController = navController, text = stringResource(
        id = R.string.withdraw
    )
    )
    {
        WithdrawScreenContent(
            paddingValues = it,
            data = data,
            actions = viewModel,
            settingsData = settingsData,
            navigationRouter = navigationRouter
        )

    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WithdrawScreenContent(
    paddingValues: PaddingValues,
    data: AddTransactionPaymentScreenData,
    actions: AddTransactionPaymentScreenActions,
    settingsData: SettingsData,
    navigationRouter: INavigationRouter

){
    var showDatePickerDialog by remember{
        mutableStateOf(false)
    }

    var showDropDown by remember {
        mutableStateOf(false)
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(paddingValues)
    ) {
        Spacer(modifier = Modifier.padding(top = 8.dp))
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),

            color = Color(0xFF8BC34A)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = stringResource(id = R.string.addnewwith),
                    color = Color.Black,

                    )
            }
        }
        Spacer(modifier = Modifier.padding(top = 8.dp))

        OutlinedTextField(
            value = data.transaction.price.toString().takeIf { it.isNotEmpty() } ?: "",
            onValueChange = {
                actions.priceChanged(it)
            },
            isError = data.priceError != null,
            label = {  Text(text = stringResource(id = R.string.price)) },
            supportingText = {
                if (data.priceError != null){
                    Text(text = data.priceError!!)
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)

        )

        val typeFromResource = stringResource(id = R.string.withdraw)
        OutlinedTextField(
            value = typeFromResource ,//.toString().takeIf { it.isNotEmpty() } ?: "",
            onValueChange = {

            },
            label = { Text(stringResource(id = R.string.paytype)) },
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
        )
        data.transaction.type = typeFromResource

        val recpi = stringResource(id = R.string.me)
        data.transaction.recipient = recpi
        OutlinedTextField(
            value = recpi,
            onValueChange = {

            },
            label = {  Text(text = stringResource(id = R.string.recipient)) },
            singleLine = true,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)

        )

        OutlinedTextField(
            value = data.transaction.detail.toString(),
            onValueChange = {
                actions.detailChanged(it)
            },
            label = {  Text(text = stringResource(id = R.string.detail)) },

            singleLine = false,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)

        )
        Spacer(modifier = Modifier.padding(top = 16.dp))

        InfoElement(icon = Icons.Default.DateRange,
            value = data.transaction.date?.let { dateUtils.getDateString(it) },
            onClick = { showDatePickerDialog = true })

        if (showDatePickerDialog) {
            CustomDatePickerDialog(
                date = data.transaction.date,
                onDismiss = { showDatePickerDialog = false },
                onDateSelected = {
                    actions.dateChanged(it)
                    showDatePickerDialog = false
                }

            )
        }
        Spacer(modifier = Modifier.padding(top = 8.dp))

        Button(
            onClick = {
                actions.saveTransaction()
            },
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF8BC34A))
        ) {
            Text(text = stringResource(id = R.string.savetransaction), color = Color.Black)
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




}
