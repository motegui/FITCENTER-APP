package ar.edu.itba.hci.fitcenter

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

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
