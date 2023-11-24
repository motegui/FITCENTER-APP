package ar.edu.itba.hci.fitcenter.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import ar.edu.itba.hci.fitcenter.api.Store
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ar.edu.itba.hci.fitcenter.R
import ar.edu.itba.hci.fitcenter.RoutineSampleData
import ar.edu.itba.hci.fitcenter.components.RoutineSearchPortrait
import ar.edu.itba.hci.fitcenter.components.RoutinesListEffect
import ar.edu.itba.hci.fitcenter.ui.theme.FitcenterTheme
@Composable
fun MyWorkoutsT(navController: NavController? = null, store: Store? = null){
    val selectedRoutine by remember { mutableStateOf(null) }
    var routines by remember {
        mutableStateOf(
            if (store != null) emptyList()
            else RoutineSampleData.sportsRoutines
        )
    }
    FitcenterTheme {
        RoutinesListEffect(navController, store) { routines = it }
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            // División Vertical con Row
            Row {
                // Columna 1
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f) // Ocupa la mitad del espacio disponible
                ) {
                    RoutineSearchPortrait(routines, navController, store, false)
                }
                // Columna 2
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxHeight()
                        .weight(1f) // Ocupa la otra mitad del espacio disponible
                ) {
                    if (selectedRoutine == null) {
                        Text(text = stringResource(R.string.difficulty))
                    }
                }
            }
        }
    }
}