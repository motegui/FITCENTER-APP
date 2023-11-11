package ar.edu.itba.hci.fitcenter


import ar.edu.itba.hci.fitcenter.ui.theme.FitcenterTheme
//import ar.edu.itba.hci.fitcenter.BuildConfig
import android.content.res.Configuration
import android.os.Bundle
import android.os.Debug
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text

import androidx.compose.material.Scaffold
import androidx.compose.material.BottomNavigation
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

    override fun onResume() {
        super.onResume()
//        if (BuildConfig.DEBUG) {
        if (Debug.isDebuggerConnected()) {
            Log.d(
                "SCREEN",
                "Keeping screen on for debugging, detach debugger and force an onResume to turn it off."
            )
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            Log.d("SCREEN", "Keeping screen on for debugging is now deactivated.")
        }
    }
}


sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int,
    val usesNav: Boolean,
    val icon: ImageVector?
) {
    object Login: Screen("login", R.string.login, false, null)
    object Profile: Screen("profile", R.string.profile, true, Icons.Filled.AccountCircle)

    object MyWorkouts: Screen("my-workouts", R.string.my_workouts, true, Icons.Filled.FitnessCenter)
    object FindWorkouts: Screen("find-workouts", R.string.find_workouts, true, Icons.Filled.Search)
}

val bottomBarItems = listOf(
    Screen.Profile,  // TO DO: move to top bar
    Screen.MyWorkouts,
    Screen.FindWorkouts,
)


@Composable
fun MyAppNavHost(
    navController: NavHostController = rememberNavController(),
    startScreen: Screen = Screen.MyWorkouts
) {
    var currentScreen by remember { mutableStateOf(startScreen) }
    Scaffold(
//        topBar = {}
        bottomBar = {
            if (currentScreen.usesNav) {
                BottomNavigation(
                    backgroundColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                ) {
//                    val navBackStackEntry by navController.currentBackStackEntryAsState()
//                    val currentDestination = navBackStackEntry?.destination
                    bottomBarItems.forEach { screen ->
                        BottomNavigationItem(
                            icon = {
                                if (screen.icon != null) {
                                    Icon(
                                        imageVector = screen.icon,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onSecondary
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
                                navController.navigate(screen.route) {
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
