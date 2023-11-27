package ar.edu.itba.hci.fitcenter.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import ar.edu.itba.hci.fitcenter.api.Models
import ar.edu.itba.hci.fitcenter.api.Store
import kotlinx.coroutines.CancellationException

@Composable
fun RoutinesListEffect(
    navController: NavController? = null,
    store: Store? = null,
    onValueChange: (List<Models.FullRoutine>) -> Unit
) {
    LaunchedEffect(store) {
        if (store == null) return@LaunchedEffect
        onValueChange(emptyList())
        try {
            onValueChange(store.fetchRoutines())
        } catch (error: Exception) {
            if (error is CancellationException) {
                return@LaunchedEffect
            }
            store.logout()
            navController?.navigate("login") {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
            }
        }
    }
}

@Composable
fun PublicRoutinesListEffect(
    navController: NavController? = null,
    store: Store? = null,
    onValueChange: (List<Models.FullRoutine>) -> Unit
) {
    LaunchedEffect(store) {
        if (store == null) return@LaunchedEffect
        onValueChange(emptyList())
        try {
            onValueChange(store.fetchPublicRoutines())
        } catch (error: Exception) {
            if (error is CancellationException) {
                return@LaunchedEffect
            }
            store.logout()
            navController?.navigate("login") {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
            }
        }
    }
}
