package ar.edu.itba.hci.fitcenter

import android.content.Context
import ar.edu.itba.hci.fitcenter.ui.theme.FitcenterTheme
import android.content.res.Configuration
import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.BottomAppBar
import androidx.compose.material.BottomNavigationItem

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.composable
import ar.edu.itba.hci.fitcenter.api.Store
import ar.edu.itba.hci.fitcenter.screens.*


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            FitcenterTheme {
                Surface(
                    modifier=Modifier.fillMaxSize(),
                    color=MaterialTheme.colorScheme.background,
                ) {
                    val store = Store.getStore(applicationContext.dataStore)
                    MainScreen(store = store)
                }
            }
        }
    }
}


val bottomBarItems = listOf("my-workouts", "find-workouts")

fun navigate(navController: NavController, route: String) {
    navController.navigate(route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // re-selecting the same item
        launchSingleTop = true
        // Restore state when re-selecting a previously selected item
        restoreState = true
    }
}

@Composable
fun SideBar(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.secondary)
    ) {
        Spacer(modifier = Modifier.weight(1f))

        sideBarItems.forEach { route ->
            val screen = screens[route]
            Icon(
                imageVector = screen.icon ?: Icons.Default.Home,
                contentDescription = null,
                tint = if (route == currentRoute) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSecondary
                },
                modifier = Modifier
                    .clickable {
                        onNavigate(route)
                    }
                    .padding(16.dp)
            )
            Spacer(modifier = Modifier.weight(1f))

        }
    }
}

val sideBarItems = listOf("profile", "my-workouts", "find-workouts")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(store: Store? = null) {
    val navController = rememberNavController()
    var currentRoute by remember { mutableStateOf("loading") }

    // Detect the current screen orientation
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    LaunchedEffect(store) {
        store?.currentUser()  // Cache current user in memory
        currentRoute = if (store?.isLoggedIn() == true) "my-workouts" else "login"
    }
    navController.addOnDestinationChangedListener { _, dest, _ ->
        currentRoute = dest.route ?: ""
    }
    val currentScreen = remember { derivedStateOf { screens[currentRoute] } }

    val topBar = @Composable {
        TopAppBar(
            colors = topAppBarColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                navigationIconContentColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onSecondary,
                actionIconContentColor = MaterialTheme.colorScheme.onSecondary
            ),
            title = {
                Text(stringResource(currentScreen.value.resourceId))
            },
            navigationIcon = {
                if (currentScreen.value.isSubPage) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.go_back)
                        )
                    }
                }
            },
            actions = {
                IconButton(onClick = { navigate(navController, "profile") }) {
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = stringResource(R.string.profile)
                    )
                }
            },
        )
    }

    val bottomBar = @Composable {
        BottomAppBar(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary
        ) {
            bottomBarItems.forEach { route ->
                val screen = screens[route]
                BottomNavigationItem(
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.onSecondary,
                    icon = {
                        if (screen.icon != null) {
                            Icon(
                                imageVector = screen.icon,
                                contentDescription = null,
                            )
                        }
                    },
                    label = {
                        Text(
                            text = stringResource(screen.resourceId),
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    },
                    selected = route == currentRoute,
                    onClick = {
                        currentRoute = route
                        navigate(navController, route)
                    }
                )
            }
        }
    }

// Sidebar
    val sidebar = @Composable {
        SideBar(
            currentRoute = currentRoute,
            onNavigate = { route ->
                currentRoute = route
                navigate(navController, route)
            }
        )
    }

    val isPortrait = !isLandscape

    Scaffold(
        topBar = { if (currentScreen.value.usesNav && isPortrait) topBar() },
        bottomBar = { if (currentScreen.value.usesNav && isPortrait) bottomBar() },
        contentWindowInsets = WindowInsets(top=0)
    ) { innerPadding ->
        if(isLandscape){
            // Display sidebar
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                sidebar()
                FitcenterNavHost(
                    navController = navController,
                    store = store,
                    startDestination = currentRoute,
                    modifier = Modifier.padding(innerPadding)
                )
            }


        } else {
            // Display the regular content
            Column {
                FitcenterNavHost(
                    navController = navController,
                    store = store,
                    startDestination = currentRoute,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }

    }
}

@Preview(name="Light Mode")
@Preview(
    name="Dark Mode",
    showBackground=true,
    uiMode=Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewMainScreen() {
    FitcenterTheme {
        Surface(
            modifier=Modifier.fillMaxSize(),
            color=MaterialTheme.colorScheme.background,
        ) {
            MainScreen()
        }
    }
}
