package ar.edu.itba.hci.fitcenter.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import ar.edu.itba.hci.fitcenter.RoutineSampleData
import androidx.navigation.NavController
import ar.edu.itba.hci.fitcenter.api.Models
import ar.edu.itba.hci.fitcenter.components.RoutineSearch
import ar.edu.itba.hci.fitcenter.components.polyvalentRoutineList
import ar.edu.itba.hci.fitcenter.api.Store
import ar.edu.itba.hci.fitcenter.ui.theme.FitcenterTheme


@Composable
fun MyWorkouts(navController: NavController? = null, store: Store? = null) {
    var routines by remember { mutableStateOf<List<Models.FullRoutine>?>(RoutineSampleData.sportsRoutines) }
    LaunchedEffect(store) {
        if (store == null) return@LaunchedEffect
        routines = null
        routines = store.fetchRoutines()
    }
    RoutineSearch(
        if (routines != null) polyvalentRoutineList(
            routines = routines!!,
            favorites = true
        ) else null,
        navController = navController
    )
}


@Preview(name="Light Mode")
@Preview(
    name="Dark Mode",
    showBackground=true,
    uiMode=Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewMyWorkouts() {
    FitcenterTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Black
        ) {
            MyWorkouts()
        }
    }
}
