package ar.edu.itba.hci.fitcenter.screens

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ar.edu.itba.hci.fitcenter.RoutineSampleData
import androidx.navigation.NavController
import ar.edu.itba.hci.fitcenter.components.MyScreen
import ar.edu.itba.hci.fitcenter.components.polyvalentRoutineList
import ar.edu.itba.hci.fitcenter.api.Store
import ar.edu.itba.hci.fitcenter.ui.theme.FitcenterTheme


@Composable
fun MyWorkouts(navController: NavController? = null, store: Store? = null) {
    RoutineSearch(polyvalentRoutineList(routines = RoutineSampleData.sportsRoutines, favorites = true), navController=navController)
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
        MyWorkouts()
    }
}
