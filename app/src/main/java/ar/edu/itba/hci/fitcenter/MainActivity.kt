package ar.edu.itba.hci.fitcenter

import ar.edu.itba.hci.fitcenter.ui.theme.FitcenterTheme
import android.content.res.Configuration
import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.composable
import ar.edu.itba.hci.fitcenter.screens.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FitcenterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier=Modifier.fillMaxSize(),
                    color=MaterialTheme.colorScheme.background,
                ) {
                    MyAppNavHost()
                }
            }
        }
    }
}


sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int,
    val usesNav: Boolean,
    val isSubPage: Boolean = false,
    val icon: ImageVector? = null
) {
    object Login: Screen(
        route = "login",
        resourceId = R.string.login,
        usesNav = false,
    )
    object Profile: Screen(
        route = "profile",
        resourceId = R.string.profile,
        usesNav = true,
        icon = Icons.Filled.AccountCircle
    )

    object MyWorkouts: Screen(
        route = "my-workouts",
        resourceId = R.string.my_workouts,
        usesNav = true,
        icon = Icons.Filled.FitnessCenter
    )
    object FindWorkouts: Screen(
        route = "find-workouts",
        resourceId = R.string.find_workouts,
        usesNav = true,
        icon = Icons.Filled.Search
    )
}

val bottomBarItems = listOf(
    Screen.MyWorkouts,
    Screen.FindWorkouts,
)

fun navigate(navController: NavHostController, route: String) {
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppNavHost(
    navController: NavHostController = rememberNavController(),
    startScreen: Screen = Screen.MyWorkouts
) {
    var currentScreen by remember { mutableStateOf(startScreen) }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    navigationIconContentColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onSecondary,
                    actionIconContentColor = MaterialTheme.colorScheme.onSecondary
                ),
                title = {
                    Text(stringResource(currentScreen.resourceId))
                },
                navigationIcon = {
                    if (currentScreen.isSubPage) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.go_back)
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = { navigate(navController, Screen.Profile.route) }) {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = stringResource(R.string.profile)
                        )
                    }
                },
            )
        },
        bottomBar = {
            if (currentScreen.usesNav) {
                BottomAppBar(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                ) {
//                    val navBackStackEntry by navController.currentBackStackEntryAsState()
//                    val currentDestination = navBackStackEntry?.destination
                    bottomBarItems.forEach { screen ->
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
//                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            selected = screen.route == currentScreen.route,
                            onClick = {
                                currentScreen = screen
                                navigate(navController, screen.route)
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController, startDestination=currentScreen.route, Modifier.padding(innerPadding)) {
            composable(Screen.Login.route) { Login(/*navController*/) }
            composable(Screen.Profile.route) { Profile(/*navController*/) }
            composable(Screen.MyWorkouts.route) { MyWorkouts(/*navController*/) }
            composable(Screen.FindWorkouts.route) { FindWorkouts(/*navController*/) }
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
fun PreviewMainActivity() {
    FitcenterTheme {
        Surface(
            modifier=Modifier.fillMaxSize(),
            color=MaterialTheme.colorScheme.background,
        ) {
            MyAppNavHost()
        }
    }
}
