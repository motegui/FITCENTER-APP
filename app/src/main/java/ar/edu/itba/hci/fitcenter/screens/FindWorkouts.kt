package ar.edu.itba.hci.fitcenter.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ar.edu.itba.hci.fitcenter.RoutineSampleData
import androidx.navigation.NavController
import ar.edu.itba.hci.fitcenter.components.RoutineSearch
import ar.edu.itba.hci.fitcenter.components.filterRoutineList
import ar.edu.itba.hci.fitcenter.api.Store
import ar.edu.itba.hci.fitcenter.components.PublicRoutinesListEffect
import ar.edu.itba.hci.fitcenter.components.RoutinesListEffect
import ar.edu.itba.hci.fitcenter.ui.theme.FitcenterTheme


@Composable
fun FindWorkouts(navController: NavController? = null, store: Store? = null) {
    var publicRoutines by remember {
        mutableStateOf(
            if (store != null) emptyList()
            else RoutineSampleData.sportsRoutines
        )
    }

    PublicRoutinesListEffect(navController, store) { publicRoutines = it }

    RoutineSearch(
        routines = publicRoutines,
        navController = navController,
        store = store,
        favorites = false
    )
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
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            FindWorkouts()
        }
    }
}
