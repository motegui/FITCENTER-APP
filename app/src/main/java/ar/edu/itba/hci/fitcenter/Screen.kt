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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.gson.GsonBuilder
import ar.edu.itba.hci.fitcenter.api.Models
import ar.edu.itba.hci.fitcenter.api.Store
import ar.edu.itba.hci.fitcenter.screens.Execution
import ar.edu.itba.hci.fitcenter.screens.FindWorkouts
import ar.edu.itba.hci.fitcenter.screens.Loading
import ar.edu.itba.hci.fitcenter.screens.Login
import ar.edu.itba.hci.fitcenter.screens.MyWorkouts
import ar.edu.itba.hci.fitcenter.screens.Profile
import ar.edu.itba.hci.fitcenter.screens.WorkoutDetails

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

    "execute" to Screen(
        resourceId = R.string.execute_routine,
        usesNav = false,
    ),

    "detailed-execute" to Screen(
        resourceId = R.string.execute_routine,
        usesNav = false,
    ),

    "workout-details" to Screen(
        resourceId = R.string.workout,
        usesNav = true,
    )
))

@Composable
fun FitcenterNavHost(navController: NavHostController, store: Store? = null, startDestination: String, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable("loading") { Loading() }
        composable("login") { Login(navController, store) }
        composable("profile") { Profile(navController, store) }
        composable("my-workouts") { MyWorkouts(navController, store) }
        composable("find-workouts") { FindWorkouts(navController, store) }
        composable("workout-details/{mega-routine}") { navBackStackEntry ->
            // Extract encoded Routine object from route
            val gson = GsonBuilder().create()
            val megaRoutineJson = navBackStackEntry.arguments?.getString("mega-routine")
            val megaRoutine = gson.fromJson(megaRoutineJson, Models.MegaRoutine::class.java)
            WorkoutDetails(navController, store, megaRoutine)
        }
        composable("execute-workout/{detailed}/{mega-routine}") { navBackStackEntry ->
            val gson = GsonBuilder().create()
            val megaRoutineJson = navBackStackEntry.arguments?.getString("mega-routine")
            val megaRoutine = gson.fromJson(megaRoutineJson, Models.MegaRoutine::class.java)
            val detailed = navBackStackEntry.arguments?.getBoolean("detailed") ?: false
            Execution(navController, routine = megaRoutine, detailed = detailed)
        }
    }
}
