package ar.edu.itba.hci.fitcenter

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

data class Screen(
    @StringRes val resourceId: Int,
    val usesNav: Boolean,
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
        usesNav = true,
        icon = Icons.Filled.FitnessCenter
    ),

    "find-workouts" to Screen(
        resourceId = R.string.find_workouts,
        usesNav = true,
        icon = Icons.Filled.Search
    ),

    "execute-routine" to Screen(
        resourceId = R.string.execute_routine,
        usesNav = false,
    ),

    "workout" to Screen(
        resourceId = R.string.workout,
        usesNav = true,
    )
))
