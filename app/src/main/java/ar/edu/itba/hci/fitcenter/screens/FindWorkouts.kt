package ar.edu.itba.hci.fitcenter.screens

import android.content.res.Configuration
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import ar.edu.itba.hci.fitcenter.RoutineSampleData
import androidx.navigation.NavController
import ar.edu.itba.hci.fitcenter.components.RoutineSearch
import ar.edu.itba.hci.fitcenter.components.polyvalentRoutineList
import ar.edu.itba.hci.fitcenter.api.Store
import ar.edu.itba.hci.fitcenter.ui.theme.FitcenterTheme


@Composable
fun FindWorkouts(navController: NavController? = null, store: Store? = null) {
    var routines by remember { mutableStateOf(RoutineSampleData.sportsRoutines) }
    var loading by remember { mutableStateOf(true) }
    LaunchedEffect(store) {
        if (store == null) return@LaunchedEffect
        loading = true
        routines = store.fetchRoutines()
        loading = false
    }
    if (!loading) {
        RoutineSearch(
            polyvalentRoutineList(
                routines = routines,
                favorites = true
            ),
            navController = navController
        )
    } else {
        CircularProgressIndicator()
    }
}


@Preview(name="Light Mode")
@Preview(
    name="Dark Mode",
    showBackground=true,
    uiMode=Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewFindWorkouts() {
    FitcenterTheme {
        FindWorkouts()
    }
}
