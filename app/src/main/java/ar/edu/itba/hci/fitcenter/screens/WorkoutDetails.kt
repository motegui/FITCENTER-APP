package ar.edu.itba.hci.fitcenter.screens

import android.content.res.Configuration
import android.util.Log
import ar.edu.itba.hci.fitcenter.api.Store
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.itba.hci.fitcenter.R
import ar.edu.itba.hci.fitcenter.SampleData
import ar.edu.itba.hci.fitcenter.api.Models
import ar.edu.itba.hci.fitcenter.components.DifficultyRating
import ar.edu.itba.hci.fitcenter.components.formatDate
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.Row as Row

@Composable
fun CycleInfo(megaCycle: Models.MegaCycle) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "x",
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            text = megaCycle.repetitions.toString(),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(end = 16.dp)
        )
        Column {
            for ((index, exercise) in megaCycle.cycleExercises.withIndex()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp, bottom = 5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = exercise.exercise.name,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        if (exercise.repetitions > 0) {
                            Row(

                            ) {
                                Text(
                                    text = "x",
                                    style = MaterialTheme.typography.bodySmall,
                                )
                                Text(
                                    text = exercise.repetitions.toString(),
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                        if (exercise.duration > 0) {
                            Row(

                            ) {
                                Text(
                                    text = exercise.duration.toString(),
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Text(
                                    text = "s",
                                    style = MaterialTheme.typography.bodySmall,
                                )
                            }
                        }
                    }
                }
                if (index < megaCycle.cycleExercises.size - 1) {
                    Divider(color = Color.Gray, thickness = 1.dp)
                }
            }
        }
    }
}

@Composable
fun CycleCard(megaCycle: Models.MegaCycle) {
    var isCycleExpanded by remember { mutableStateOf(false) }
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = Color.Black,
        shadowElevation = 4.dp,
        modifier = Modifier
            .animateContentSize()
            .padding(top = 5.dp, start = 16.dp, end = 16.dp, bottom = 5.dp)
            .height(46.dp)
            .fillMaxWidth()
            .clickable { isCycleExpanded = !isCycleExpanded }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 1.dp, start = 16.dp, end = 6.dp, bottom = 1.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = megaCycle.name,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.Green
                )
            )
            Icon(
                imageVector = if (isCycleExpanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                tint = Color.Green,
                contentDescription = "Expand/Collapse",
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
    if (isCycleExpanded) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            shadowElevation = 4.dp,
            color = Color.White,
            modifier = Modifier
                .animateContentSize()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 16.dp, end = 16.dp, bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CycleInfo(megaCycle)
            }
        }
    }
}

@Composable
fun Title(routine: Models.FullRoutine? = null, onFavoriteChanged: (Boolean) -> Unit) {
    var isFavorite by remember { mutableStateOf(routine?.isFavorite ?: false) }
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = Color.LightGray,
        shadowElevation = 4.dp,
        modifier = Modifier
            .animateContentSize()
            .padding(16.dp) // Ajustar el padding según tus preferencias
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium) // Redondear los bordes
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, start = 16.dp, end = 6.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (routine != null) {
                // Texto a la izquierda
                Text(
                    text = routine.name,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold, // Hacer el texto en negrita
                        color = Color.Black
                    ),
                    modifier = Modifier.weight(1f) // Utilizar weight para ocupar el espacio disponible
                )

                // Corazón a la derecha
                IconButton(
                    onClick = {
                        isFavorite = !isFavorite
                        onFavoriteChanged(isFavorite)
                    },
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Icon(
                        imageVector = if (isFavorite == true) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        tint = if (isFavorite == true) Color(0xFFFF7F7F) else Color.Black,
                        contentDescription = "Favorite"
                    )
                }
            }
        }
    }
}

@Composable
fun Info(routine: Models.FullRoutine? = null) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 4.dp,
        modifier = Modifier
            .animateContentSize()
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 16.dp, end = 16.dp, bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.difficulty),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold, // Hacer el texto en negrita
                        color = Color.Black
                    ),
                    modifier = Modifier.weight(1f) // Utilizar weight para ocupar el espacio disponible
                )
                if (routine != null) {
                    DifficultyRating(difficulty = routine.difficulty)
                }
            }
            Divider(color = Color.Gray, thickness = 1.dp)
            if (routine?.category != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, start = 16.dp, end = 16.dp, bottom = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.category),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold, // Hacer el texto en negrita
                            color = Color.Black
                        ),
                        modifier = Modifier.weight(1f) // Utilizar weight para ocupar el espacio disponible
                    )
                    Text(
                        text = routine.category.name,
                        style = MaterialTheme.typography.titleMedium,
                        //modifier = Modifier.weight(1f)
                    )

                }
            }
            Divider(color = Color.Gray, thickness = 1.dp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 16.dp, end = 16.dp, bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.date),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold, // Hacer el texto en negrita
                        color = Color.Black
                    ),
                    modifier = Modifier.weight(1f) // Utilizar weight para ocupar el espacio disponible
                )
                if (routine != null) {
                    Text(
                        text = formatDate(routine.date),
                        style = MaterialTheme.typography.titleMedium,
                        //modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }

}

@Composable
fun EquipmentInfo(routine: Models.FullRoutine? = null) {
    var isEquipmentExpanded by remember { mutableStateOf(false) }
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = Color.White,
        shadowElevation = 4.dp,
        modifier = Modifier
            .animateContentSize()
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 1.dp, start = 16.dp, end = 6.dp, bottom = 1.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.equipment),
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            )
            IconButton(
                onClick = {
                    isEquipmentExpanded = !isEquipmentExpanded
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(
                    imageVector = if (isEquipmentExpanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                    tint = Color.Black,
                    contentDescription = "Expand/Collapse",
                )
            }
        }
    }
    if (isEquipmentExpanded) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            shadowElevation = 4.dp,
            color = Color.White,
            modifier = Modifier
                .animateContentSize()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 16.dp, end = 16.dp, bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "the equipment here",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Composable
fun DetailedModeSetting(isDetailed: MutableState<Boolean>) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = Color.White,
        shadowElevation = 4.dp,
        modifier = Modifier
            .animateContentSize()
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(top = 1.dp, start = 16.dp, end = 6.dp, bottom = 1.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = stringResource(R.string.detailed_mode),

                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                )
                Text(
                    text = stringResource(R.string.detailed_description),
                    style = MaterialTheme.typography.bodySmall
                )

            }
            Checkbox(
                checked = isDetailed.value,
                onCheckedChange = {
                    isDetailed.value = it
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}

fun startRoutine(
    navController: NavController,
    megaRoutine: Models.MegaRoutine,
    isDetailed: Boolean
) {
    val gson = GsonBuilder().create()
    val megaRoutineJson = gson.toJson(megaRoutine)
    try {
        navController.navigate(
            "execute-workout/?detailedMode=$isDetailed&megaRoutineJson=$megaRoutineJson"
        )
    } catch (e: Exception){
        throw e
    }
}

@Composable
fun StartButton(
    navController: NavController? = null,
    store: Store? = null,
    megaRoutine: Models.MegaRoutine,
    isDetailed: MutableState<Boolean>
) {
    val scope = rememberCoroutineScope()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                if (navController == null || store == null) return@Button
                scope.launch {
                    startRoutine(navController, megaRoutine, isDetailed.value)
                }
            }
        ) {
            Text(stringResource(R.string.start))
        }
    }
}


@Composable
fun WorkoutDetails(
    navController: NavController? = null,
    store: Store? = null,
    routineId: Long
) {
    var megaRoutine by remember { mutableStateOf<Models.MegaRoutine?>(null) }
    LaunchedEffect(store) {
        if (store == null) throw Exception("Store is missing")
        try {
            val routine = store.fetchRoutine(routineId)
            megaRoutine = Models.MegaRoutine(store, routine)
            Log.d("WorkoutDetails", megaRoutine.toString())
        } catch (error: Exception) {
            if (error is CancellationException) {
                Log.w("WorkoutDetails", "Left composition while fetching MegaRoutine")
                return@LaunchedEffect
            }
        }
    }

    if (megaRoutine == null) {
        Loading()
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
            DetailedModeSetting(isDetailed)
            StartButton(navController, store, megaRoutine!!, isDetailed)
        }
    }
}

@Composable
fun WorkoutDetails2(
    navController: NavController? = null,
    store: Store? = null,
    megaRoutine: Models.MegaRoutine = SampleData.megaRoutine,
) {
    val isDetailed = remember { mutableStateOf(false) }
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    val scope = rememberCoroutineScope()


    Column {
        // Title at the top, full width
        Title(megaRoutine) {
            scope.launch {
                store?.setFavorite(megaRoutine.id, it)
            }
        }

        // If in landscape, use a Row for the two columns
        if (isLandscape) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                // Left column for Info and EquipmentInfo
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    Info(megaRoutine)
                    EquipmentInfo(megaRoutine)
                    StartButton(navController, store, megaRoutine, isDetailed)
                }

                // Right column for the list of cycles
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                ) {
                    megaRoutine.megaCycles.forEach { megaCycle ->
                        CycleCard(megaCycle)
                    }
                }
            }
        } else {
            // Portrait mode: Info, EquipmentInfo, and Cycles in a single column
            Info(megaRoutine)
            EquipmentInfo(megaRoutine)
            megaRoutine.megaCycles.forEach { megaCycle ->
                CycleCard(megaCycle)
            }
            DetailedModeSetting(isDetailed)
            StartButton(navController, store, megaRoutine, isDetailed)
        }


    }
}


//@Preview
//@Composable
//fun PreviewRoutineDetail() {
//    FitcenterTheme {
//        Surface(
//            modifier = Modifier.fillMaxSize(),
//            color = MaterialTheme.colorScheme.background,
//        ) {
//            WorkoutDetails()
//        }
//    }
//}
