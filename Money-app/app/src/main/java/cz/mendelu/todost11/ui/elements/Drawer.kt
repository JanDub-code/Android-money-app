package cz.mendelu.todost11.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberDrawerState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.rememberAsyncImagePainter
import cz.mendelu.todost11.R
import cz.mendelu.todost11.navigation.Destination
import cz.mendelu.todost11.ui.screens.SettingsUIState
import cz.mendelu.todost11.ui.screens.SettingsViewModel
import cz.mendelu.todost11.ui.screens.SettingsData
import cz.mendelu.todost11.navigation.INavigationRouter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.File


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Drawer(
    navigationRouter: INavigationRouter,
    navController: NavController,
    text: String,
    content: @Composable (PaddingValues) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: Destination.AddEditTaskScreen.route
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val viewModel = hiltViewModel<SettingsViewModel>()
    var data by remember {
        mutableStateOf(SettingsData())
    }

    val settings = viewModel.settingsUIState.collectAsStateWithLifecycle()

    settings.value.let {
        when(it) {
            is SettingsUIState.Loading -> {
                viewModel.loadSettings()
            }

            is SettingsUIState.Success -> {

            }

            is SettingsUIState.Error -> {

            }

            is SettingsUIState.ScreenDataChanged -> {
                data = it.data
            }
        }

    }

    val imgFile = File(data.userSettings.photo!!)

    ModalNavigationDrawer(drawerContent = {
        ModalDrawerSheet(
            modifier = Modifier,
            drawerContainerColor = Color(0xFF8BC34A)
        ) {
            Column(
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .height(140.dp)
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                if(data.userSettings.photo != "") {
                    Image(modifier = Modifier
                        .height(150.dp)
                        .width(150.dp)
                        .clip(CircleShape),
                        contentScale = ContentScale.Crop,
                        painter = rememberAsyncImagePainter(imgFile),
                        contentDescription = null
                    )
                } else {
                    Card(
                        modifier = Modifier
                            .width(150.dp)
                            .height(150.dp),
                        shape = RoundedCornerShape(900.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.LightGray
                        )
                    ) {

                    }
                }
            }
            Spacer(modifier = Modifier.height(25.dp))
            NavigationDrawerItem(
                label = {
                    Text(
                        text = stringResource(id = R.string.main_title),
                        color = Color.Black
                    )
                },
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = Color(0xFF3B6E1A),
                    unselectedContainerColor = Color(0xFF8BC34A)
                ),
                selected = currentRoute == Destination.TaskListScreen.route,
                onClick = {
                    navigationRouter.navigateToMain()
                    coroutineScope.launch { drawerState.close() }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.home),
                        tint = Color.Black,
                        contentDescription = null
                    )
                },
            )


            NavigationDrawerItem(
                label = {
                    Text(
                        text = stringResource(id = R.string.where),
                        color = Color.Black
                    )
                },
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = Color(0xFF3B6E1A),
                    unselectedContainerColor = Color(0xFF8BC34A)
                ),
                selected = currentRoute == Destination.whereScreen.route,
                onClick = {
                    navigationRouter.navigateToWhereScreen()
                    coroutineScope.launch { drawerState.close() }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.where),
                        tint = Color.Black,
                        contentDescription = null
                    )
                },
            )

            NavigationDrawerItem(
                label = {
                    Text(
                        text = stringResource(id = R.string.statistic_title),
                        color = Color.Black
                    )
                },
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = Color(0xFF3B6E1A),
                    unselectedContainerColor = Color(0xFF8BC34A)
                ),
                selected = currentRoute == Destination.Settings.route,
                onClick = {
                    navigationRouter.navigateToStatistics(null)
                    coroutineScope.launch { drawerState.close() }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.statistics),
                        tint = Color.Black,
                        contentDescription = null
                    )
                },

            )

            NavigationDrawerItem(
                label = {
                    Text(
                        text = stringResource(id = R.string.settings_title),
                        color = Color.Black
                    )
                },
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = Color(0xFF3B6E1A),
                    unselectedContainerColor = Color(0xFF8BC34A)
                ),
                selected = currentRoute == Destination.Settings.route,
                onClick = {
                    navigationRouter.navigateToSettings(1)
                    coroutineScope.launch { drawerState.close() }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.settings),
                        tint = Color.Black,
                        contentDescription = null
                    )
                },
            )
        }
    }, drawerState = drawerState) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = text,
                            color = Color.Black
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch { drawerState.open() }
                        },
                            content = {
                                Image(
                                    painter = painterResource(R.drawable.menu1),
                                    contentDescription = "dddd",
                                )
                            })
                    },
                    colors = topAppBarColors(containerColor = Color(0xFF8BC34A)),
                    actions = {
                        if(data.userSettings.photo != "") {
                            Image(
                                painter = rememberAsyncImagePainter(imgFile),
                                contentDescription = null,
                                modifier = Modifier
                                    .height(50.dp)
                                    .width(50.dp)
                                    .clip(CircleShape)

                            )
                        } else {
                            Card(
                                modifier = Modifier
                                    .height(40.dp)
                                    .width(40.dp),
                                shape = RoundedCornerShape(20.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.LightGray
                                )
                            ) {

                            }
                        }
                    }
                )
            },
            modifier = Modifier
        ) { paddingValues ->
            content(paddingValues)
        }
    }
}