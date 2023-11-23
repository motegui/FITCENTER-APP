package ar.edu.itba.hci.fitcenter.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.itba.hci.fitcenter.SampleData
import ar.edu.itba.hci.fitcenter.api.Models
import ar.edu.itba.hci.fitcenter.api.Store
import ar.edu.itba.hci.fitcenter.screens.WorkoutDetails
import ar.edu.itba.hci.fitcenter.ui.theme.FitcenterTheme


@Composable
fun DetectDeviceTypeScreen() {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp

    val screenType = if (screenWidthDp > 600.dp) {
        "Tablet"
    } else {
        "Phone"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Device Type:",
            style = MaterialTheme.typography.displayLarge,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = screenType,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
fun TabletScreen(
    navController: NavController? = null,
    store: Store? = null,
    routines: List<Models.FullRoutine>? = null,
    megaRoutine: Models.MegaRoutine = SampleData.megaRoutine,
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Left side with RoutineSearchVertical
        RoutineSearchPortrait(routines = routines, navController = navController, store = store)

        // Right side with WorkoutDetails
        WorkoutDetails(navController = navController, store = store, megaRoutine = megaRoutine)
    }
}


@Preview
@Composable
fun DetectDeviceTypeScreenPreview() {
    FitcenterTheme {
        DetectDeviceTypeScreen()
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
                TabletScreen()
            }
        }
}

