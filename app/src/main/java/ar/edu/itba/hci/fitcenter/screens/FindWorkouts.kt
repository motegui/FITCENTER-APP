package ar.edu.itba.hci.fitcenter.screens

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ar.edu.itba.hci.fitcenter.RoutineSampleData
import ar.edu.itba.hci.fitcenter.SortedRoutineList
import ar.edu.itba.hci.fitcenter.SortingCriterion
import ar.edu.itba.hci.fitcenter.api.Store
import ar.edu.itba.hci.fitcenter.ui.theme.FitcenterTheme



@Composable
fun FindWorkouts(store: Store? = null) {
    SortedRoutineList(routines = RoutineSampleData.sportsRoutines, sortingCriterion = SortingCriterion.DATE)

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
