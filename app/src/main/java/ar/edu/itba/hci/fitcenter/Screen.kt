package ar.edu.itba.hci.fitcenter

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.google.gson.GsonBuilder
import ar.edu.itba.hci.fitcenter.api.Models
import ar.edu.itba.hci.fitcenter.api.Store
import ar.edu.itba.hci.fitcenter.screens.Execution
import ar.edu.itba.hci.fitcenter.screens.FindWorkouts
import ar.edu.itba.hci.fitcenter.screens.FindWorkoutsT
import ar.edu.itba.hci.fitcenter.screens.Loading
import ar.edu.itba.hci.fitcenter.screens.Login
import ar.edu.itba.hci.fitcenter.screens.MyWorkouts
import ar.edu.itba.hci.fitcenter.screens.Profile
import ar.edu.itba.hci.fitcenter.screens.ProfileL
import ar.edu.itba.hci.fitcenter.screens.WorkoutDetails
import ar.edu.itba.hci.fitcenter.screens.MyWorkoutsT

data class Screen(
    @StringRes val resourceId: Int,
    val usesNav: Boolean,
    @StringRes val navResourceId: Int? = null,
    val isSubPage: Boolean = false,
    val icon: ImageVector? = null
)

class NonNullableMap<K, V>(private val content: Map<K, V>) {
    operator fun get(key: K) = content[key] ?: throw Exception("Key $key does not exist")
}


val screens = NonNullableMap(mapOf(
    "loading" to Screen(
        resourceId = R.string.please_wait,
        usesNav = false,
    ),

    "login" to Screen(
        resourceId = R.string.login,
        usesNav = false,
    ),

    "profile" to Screen(
        resourceId = R.string.profile,
        usesNav = true,
        icon = Icons.Filled.AccountCircle
    ),

    "my-workouts" to Screen(
        resourceId = R.string.my_workouts,
        navResourceId = R.string.workouts,
        usesNav = true,
        icon = Icons.Filled.FitnessCenter
    ),

    "find-workouts" to Screen(
        resourceId = R.string.find_workouts,
        navResourceId = R.string.find,
        usesNav = true,
        icon = Icons.Filled.Search
    ),

    "execute-workout/?detailedMode={detailedMode}&megaRoutineJson={megaRoutineJson}" to Screen(
        resourceId = R.string.execute_routine,
        usesNav = false,
    ),

    "detailed-execute" to Screen(
        resourceId = R.string.execute_routine,
        usesNav = false,
    ),

    "workout-details/{id}" to Screen(
        isSubPage = true,
        resourceId = R.string.workout,
        usesNav = true,
    )
))

const val uri = "www.fitcenter.com"

var lastRoutineId: Long = 0
var lastMegaRoutine: Models.MegaRoutine? = null

@Composable
fun FitcenterNavHost(
    navController: NavHostController,
    store: Store? = null,
    startDestination: String,
    modifier: Modifier,
    isLandscape: Boolean,
    isDeviceTablet: Boolean
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable("loading") { Loading() }
        composable("login") { Login(navController, store) }
        if(isLandscape){
            composable("profile") { ProfileL(navController, store) }}
        else{
            composable("profile") { Profile(navController, store) }}
        if(isDeviceTablet){
            composable("my-workouts") { MyWorkoutsT(navController, store) }
            composable("find-workouts") { FindWorkoutsT(navController, store) }

        }
        else{
            composable("my-workouts") { MyWorkouts(navController, store) }
            composable("find-workouts") { FindWorkouts(navController, store) }
        }
        composable(
            route = "workout-details/{id}",
            deepLinks = listOf(
                navDeepLink { uriPattern = "android-app://ar.edu.itba.hci.fitcenter/workout-details/{id}" },
                navDeepLink { uriPattern = "http://www.fitcenter.com/view-workout/{id}" },
                navDeepLink { uriPattern = "https://www.fitcenter.com/view-workout/{id}" }
            ),
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) {
            var routineId = it.arguments?.getLong("id")
            if (routineId == null || routineId == 0L) {
                // Band-aid for weird duplicate requests with no ID after a legitimate one
                routineId = lastRoutineId
            }
            WorkoutDetails(navController, store, routineId)
            lastRoutineId = routineId
        }
        composable(
            route = "execute-workout/?detailedMode={detailedMode}&megaRoutineJson={megaRoutineJson}",
            arguments = listOf(
                navArgument("detailedMode") { type = NavType.BoolType },
                navArgument("megaRoutineJson") { type = NavType.StringType }
            )
        ) { navBackStackEntry ->
            val gson = GsonBuilder().create()
            val megaRoutineJson = navBackStackEntry.arguments?.getString("megaRoutineJson")
            val megaRoutine = if (megaRoutineJson != null) {
                gson.fromJson(megaRoutineJson, Models.MegaRoutine::class.java)
            } else {
                lastMegaRoutine ?: throw Exception("all hope is lost")
            }
            val detailed = navBackStackEntry.arguments?.getBoolean("detailedMode") ?: false
            Execution(navController, megaRoutine, detailed)
            lastMegaRoutine = megaRoutine
        }
    }
}
