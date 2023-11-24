package ar.edu.itba.hci.fitcenter.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.itba.hci.fitcenter.RoutineSampleData
import ar.edu.itba.hci.fitcenter.SampleData
import ar.edu.itba.hci.fitcenter.api.Models
import ar.edu.itba.hci.fitcenter.api.Store
import ar.edu.itba.hci.fitcenter.screens.WorkoutDetails
import ar.edu.itba.hci.fitcenter.ui.theme.FitcenterTheme


@Composable
fun DetectDeviceTypeScreen(routines: List<Models.FullRoutine>,
                           navController: NavController? = null,
                           store: Store? = null,
                           megaRoutine: Models.MegaRoutine = SampleData.megaRoutine,
                           favorites: Boolean
) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp

    if (screenWidthDp > 600.dp) {
        TabletScreen(routines, navController, store, megaRoutine, favorites)
    } else {
        RoutineSearch(routines, navController, store, favorites)
    }
}

@Composable
fun TabletScreen(
    routines: List<Models.FullRoutine>,
    navController: NavController? = null,
    store: Store? = null,
    megaRoutine: Models.MegaRoutine = SampleData.megaRoutine,
    favorites: Boolean
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Left side with RoutineSearchPortrait
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            RoutineSearchPortrait(
                routines = routines,
                navController = navController,
                store = store,
                favorites = favorites
            )
        }

        // Divider
        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(2.dp),
            color = Color.LightGray
        )

        // Right side with WorkoutDetails
        Column(
            modifier = Modifier
                .weight(
                    if (isLandscape) {
                        1.8f
                    } else {
                        1f
                    }
                )
                .fillMaxHeight()
        ) {
            WorkoutDetails(
                navController = navController,
                store = store,
                id = megaRoutine.id.toInt(),
            )
        }
    }
}



@Preview
@Composable
fun DetectDeviceTypeScreenPreview() {
    FitcenterTheme {
    }
}

@Preview
@Composable
fun TabletScreenPreview() {
    FitcenterTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            TabletScreen(RoutineSampleData.sportsRoutines, favorites = true)
        }
    }
}

