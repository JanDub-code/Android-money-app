package cz.mendelu.todost11.ui.screens

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import cz.mendelu.todost11.R
import cz.mendelu.todost11.navigation.INavigationRouter
import cz.mendelu.todost11.ui.elements.Drawer
import java.io.File


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navigationRouter: INavigationRouter,navController: NavHostController = rememberNavController()){

    val viewModel = hiltViewModel<SettingsViewModel>()
    val settings = viewModel.settingsUIState.collectAsStateWithLifecycle()
    val actions = viewModel
    var data by remember {
        mutableStateOf(SettingsData())
    }

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val context = LocalContext.current

    var galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                imageUri = it
            }
        }
    )

    val coroutineScope = rememberCoroutineScope()


    settings.value.let {
        when(it){
            is SettingsUIState.Loading -> {
                viewModel.loadSettings()
            }
            is SettingsUIState.Success -> {

            }

            is SettingsUIState.Error -> {
                // Handle error state
                Text("Error: ")
            }



            is SettingsUIState.ScreenDataChanged -> {
                data=it.data
            }
        }

    }
    Drawer(navigationRouter = navigationRouter, navController = navController, text = stringResource(
        id = R.string.settings_title
    )) {
        SettingsScreenContent(
            paddingValues = it,
            navigationRouter = navigationRouter,
            galleryLauncher = galleryLauncher,
            imageUri = imageUri,
            actions = actions,
            data = data,
            context = context,
            navRouter = navigationRouter




        )

    }
    }




//@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun SettingsScreenContent(
    paddingValues: PaddingValues,
    navigationRouter: INavigationRouter,
    galleryLauncher: ActivityResultLauncher<String>,
    imageUri: Uri?,
    actions: SettingsActions,
    navRouter: INavigationRouter,
    data: SettingsData,
    context: Context
){

    var selectedImageUri by remember {
        mutableStateOf<Uri?>(Uri.parse(data.userSettings.photo))
    }

    val photoPickerLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            selectedImageUri = it
            actions.PhotoChanged(it, context)
        }
    )

    val imgFile = File(data.userSettings.photo!!)

    Box(modifier = Modifier
        .fillMaxSize()
    ) {
        Column(        horizontalAlignment = Alignment.CenterHorizontally,

            modifier = Modifier
                .padding(paddingValues)
                .padding(start = 8.dp, end = 8.dp, top = 5.dp, bottom = 0.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.padding(top = 24.dp))

            Row(modifier = Modifier
                .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if(data.userSettings.photo != "") {
                    Image(modifier = Modifier
                        .height(160.dp)
                        .width(160.dp)
                        .clip(CircleShape),
                        contentScale = ContentScale.Crop,
                        painter = rememberAsyncImagePainter(imgFile),
                        contentDescription = null
                    )
                } else {
                    Card(
                        modifier = Modifier
                            .width(160.dp)
                            .height(160.dp),
                        shape = RoundedCornerShape(900.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.LightGray
                        )
                    ) {

                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier
                .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier,
                    onClick = {
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                    colors = ButtonDefaults.buttonColors( Color(0xFF8BC34A))

                ) {
                    Text(text = stringResource(id = R.string.choose_picture), textAlign = TextAlign.Center,color = Color.Black)
                }


            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = stringResource(id = R.string.paylimit) + ":")

            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = data.userSettings.paylimit.toString(),
                onValueChange = {
                    actions.payLimitChanged(it)
                },
                isError = data.textError != null,
                label = {  Text(text = stringResource(id = R.string.paylimit)) },
                supportingText = {
                    if (data.textError != null){
                        Text(text = data.textError!!)
                    }
                },
                singleLine = true,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)

            )
            Text(text = stringResource(id = R.string.withdrawlimit))
            OutlinedTextField(
                value = data.userSettings.withdrawalLimit.toString(),
                onValueChange = {
                    actions.withdrawlimitChanged(it)
                },
                isError = data.textError != null,
                label = {  Text(text = stringResource(id = R.string.withdrawlimit)) },
                supportingText = {
                    if (data.textError != null){
                        Text(text = data.textError!!)
                    }
                },
                singleLine = true,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)

            )

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    actions.saveSettings()
                    navRouter.navigateToSettings(1)
                },
                colors = ButtonDefaults.buttonColors( Color(0xFF8BC34A))
            ) {
                Text(text = stringResource(id = R.string.save_button),color = Color.Black)
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
        Row(
            modifier = Modifier
                .padding(end = 0.dp, bottom = 16.dp)
                .align(Alignment.BottomCenter)
                .border(width = 1.dp, color = Color.Black)
                .background(Color.White)
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                text = stringResource(id = R.string.appversion),
                color = Color.Black,
                fontSize = 12.sp //
            )
        }

    }
}


    /*
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileImage()
        Image(
            painter = rememberImagePainter("https://cataas.com/cat"),
            contentDescription = "Selected Image",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                galleryLauncher.launch("image/*")



            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Pick an Image from Gallery")
        }

        // Display selected image if available
        imageUri?.let { uri ->

            Image(
                painter = rememberImagePainter(uri),
                contentDescription = "Selected Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                contentScale = ContentScale.Crop
            )
        }/*
        Box(modifier = Modifier
            .height(300.dp)
            .width(300.dp)
        ){
            val painter = rememberImagePainter(data = imageUri,
                builder = {

                })
            val painterState = painter.state
            Image(painter = painter, contentDescription = "logo")
        }*/

        Button(
            onClick = {

            },
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Text(text = "Save record")
        }




        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Change",
            fontSize = 18.sp,
            color = Color(0xFF0000FF)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = "Monthly selection limit",
            onValueChange = { },
            label = { Text("Monthly selection limit") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = "Max payment limit",
            onValueChange = { },
            label = { Text("Max payment limit") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* Save action */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF8BC34A))
        ) {
            Text(text = "Save")
        }
    }
}





@Composable
fun SettingsListRow(record: Record,
                onClick:()->Unit,
                actions: MainActions) {
    RecordItem(placeFrom = record.from, placeTo = record.to, numberOfKm = record.numberOfKm, priceOfFuel = record.priceOfFuel, fuelType = record.fuelType)



}



@Composable
fun ProfileImage() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(100.dp)
            .background(color = androidx.compose.ui.graphics.Color.Gray, shape = CircleShape)
    ) {
        val image: Painter = painterResource(R.drawable.bank) // Replace with your actual image
        Image(
            painter = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .background(color = androidx.compose.ui.graphics.Color.Gray, shape = CircleShape)
        )
    }

}

*/