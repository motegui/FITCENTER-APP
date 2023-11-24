package ar.edu.itba.hci.fitcenter.screens

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import ar.edu.itba.hci.fitcenter.api.Store
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ar.edu.itba.hci.fitcenter.R
import ar.edu.itba.hci.fitcenter.RoutineSampleData
import ar.edu.itba.hci.fitcenter.api.Models
import ar.edu.itba.hci.fitcenter.components.PublicRoutinesListEffect
import ar.edu.itba.hci.fitcenter.components.RoutineSearchPortrait
import ar.edu.itba.hci.fitcenter.ui.theme.FitcenterTheme
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException

@Composable
fun FindWorkoutsT(navController: NavController? = null, store: Store? = null){
    var selectedRoutineId by remember { mutableStateOf<Long?>(null) }
    var routines by remember {
        mutableStateOf(
            if (store != null) emptyList()
            else RoutineSampleData.sportsRoutines
        )
    }
    FitcenterTheme {
        PublicRoutinesListEffect(navController, store) { routines = it }
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
                    RoutineSearchPortrait(routines, navController, store, false, true, onCardClick = { clickedId ->
                        // Almacena el ID de la card cuando se hace clic
                        selectedRoutineId = clickedId
                    })
                }
                // Columna 2
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxHeight()
                        .weight(1f), // Ocupa la otra mitad del espacio disponible
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedRoutineId == null) {
                        Text(text = stringResource(R.string.no_selected_routine),
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(30.dp),
                                textAlign = TextAlign.Center )                  }
                    else{
                        var megaRoutine by remember { mutableStateOf<Models.MegaRoutine?>(null) }
                        if (megaRoutine == null || megaRoutine!!.id != selectedRoutineId!!.toLong()) {
                            Loading()
                            LaunchedEffect(store) {
                                if (store == null) throw Exception("Store is missing")
                                try {
                                    val routine = store.fetchRoutine(selectedRoutineId!!.toLong())
                                    megaRoutine = Models.MegaRoutine(store, routine)
                                    Log.d("WorkoutDetails", megaRoutine.toString())
                                } catch (error: Exception) {
                                    if (error is CancellationException) {
                                        return@LaunchedEffect
                                    }
                                }
                            }
                        } else {
                            val isDetailed = remember { mutableStateOf(false) }
                            val scope = rememberCoroutineScope()
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .verticalScroll(rememberScrollState())
                                    .padding(bottom = 36.dp)
                            ) {
                                Title(routine = megaRoutine) {
                                    if (megaRoutine == null) return@Title
                                    scope.launch {
                                        store?.setFavorite(megaRoutine!!.id, it)
                                    }
                                }
                                Info(routine = megaRoutine)
                                EquipmentInfo(routine = megaRoutine)
                                megaRoutine!!.megaCycles.forEach { megaCycle ->
                                    CycleCard(megaCycle)
                                }
                                DetailedModeSetting(navController, store, megaRoutine!!)
                                //StartButton(navController, store, megaRoutine!!, isDetailed)
                            }
                        }
                    }
                }
            }
        }
    }
}
