package ar.edu.itba.hci.fitcenter.screens

import android.content.res.Configuration
import ar.edu.itba.hci.fitcenter.api.Store
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.itba.hci.fitcenter.R
import ar.edu.itba.hci.fitcenter.RoutineSampleData
import ar.edu.itba.hci.fitcenter.api.Models
import ar.edu.itba.hci.fitcenter.components.DifficultyRating
import ar.edu.itba.hci.fitcenter.components.formatDate
import ar.edu.itba.hci.fitcenter.ui.theme.FitcenterTheme
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.Row as Row

@Composable
fun CycleInfo(cycle: Models.FullCycle) {
    var cycleExercises: List<Models.FullCycleExercise> = RoutineSampleData.cylceInfo
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "x",
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            text = cycle.repetitions.toString(),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(end = 16.dp)
        )
        Column {
            for ((index, exercise) in cycleExercises.withIndex()) {
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
                if (index < cycleExercises.size - 1) {
                    Divider(color = Color.Gray, thickness = 1.dp)
                }
            }
        }
    }
}

@Composable
fun CycleCard(cycle: Models.FullCycle) {
    var isCycleExpanded by remember { mutableStateOf(false) }
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = Color.Black,
        shadowElevation = 4.dp,
        modifier = Modifier
            .animateContentSize()
            .padding(top = 5.dp, start = 16.dp, end = 16.dp, bottom = 5.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 1.dp, start = 16.dp, end = 6.dp, bottom = 1.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = cycle.name,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.Green
                )
            )
            IconButton(
                onClick = {
                    isCycleExpanded = !isCycleExpanded
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(
                    imageVector = if (isCycleExpanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                    tint = Color.Green,
                    contentDescription = "Expand/Collapse",
                )
            }
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
                CycleInfo(cycle = cycle)
            }
        }
    }
}

@Composable
fun Title(routine: Models.FullRoutine? = null) {
    var isFavorite by remember { mutableStateOf(routine?.isFavorite) }
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
                        isFavorite = !isFavorite!!
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
                if (routine != null) {
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
fun Details(routine: Models.FullRoutine? = null) {
    var isDetailed by remember { mutableStateOf(false) }
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
                checked = isDetailed,
                onCheckedChange = {
                    isDetailed = it
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}

suspend fun startRoutine(
    navController: NavController,
    store: Store,
    routine: Models.FullRoutine? = null,
    isDetailed: Boolean
) {
    val gson = GsonBuilder().create()
    val megaRoutineJson = gson.toJson(routine?.let { Models.MegaRoutine(store, it) })
    navController.navigate(
        "execute-workout/{detailed}/{mega-routine}"
            .replace(
                oldValue = "{detailed}",
                newValue = isDetailed.toString()
            )
            .replace(
                oldValue = "{mega-routine}",
                newValue = megaRoutineJson
            )
    )
}

@Composable
fun StartButton(navController: NavController? = null, store: Store? = null, routine: Models.FullRoutine? = null){
    var isFavorite by remember { mutableStateOf(routine?.isFavorite) }
    var isEquipmentExpanded by remember { mutableStateOf(false) }
    var isDetailed by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(isDetailed){
        Button(
            onClick = { navController?.navigate("execute") }
        ) {
            Text(stringResource(R.string.start))
        }}
        if(!isDetailed){
            Button(
                onClick = { navController?.navigate("detailed-execute") }
            ) {
                Text(stringResource(R.string.start))
            }
        }
        Button(
            onClick = {
                if (navController == null || store == null) return@Button
                scope.launch {
                    startRoutine(navController, store, routine, isDetailed)
                }
            }
        ) {
            Text(stringResource(R.string.start))
        }
    }
}


@Composable
fun WorkoutDetails(navController: NavController? = null, store: Store? = null, routine:Models.FullRoutine? = null) {
    val routines: List<Models.FullRoutine?> = listOf(routine)
    val cycles: Models.Cycles = RoutineSampleData.cyclesRoutine

    val scope = rememberCoroutineScope()

    LazyColumn {
        items(routines) {
            Title(routine)
            Info(routine = routine)
            EquipmentInfo(routine = routine)
        }
        if (cycles != null) {
            items(cycles.content) { cycle ->
                CycleCard(cycle)
            }
        }
        items(routines){
            StartButton(navController, store, routine)
        }
    }
}

@Composable
fun WorkoutDetails2(navController: NavController? = null, store: Store? = null, routine: Models.FullRoutine? = null) {
    val routines: List<Models.FullRoutine?> = listOf(routine)
    val cycles: Models.Cycles = RoutineSampleData.cyclesRoutine

    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    LazyColumn {
        // Title at the top, full width
        items(routines) {
            Title(routine)
        }

        // If in landscape, use a Row for the two columns
        if (isLandscape) {
            items(routines) {
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
                        Info(routine = routine)
                        EquipmentInfo(routine = routine)
                        StartButton(navController, store, routine)
                    }

                    // Right column for the list of cycles
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    ) {
                        if (cycles != null) {
                            cycles.content.forEach { cycle ->
                                CycleCard(cycle)
                            }
                        }
                    }
                }
            }
        } else {
            // Portrait mode: Info, EquipmentInfo, and Cycles in a single column
            items(routines) {
                Info(routine = routine)
                EquipmentInfo(routine = routine)

                if (cycles != null) {
                    cycles.content.forEach { cycle ->
                        CycleCard(cycle)
                    }
                }
            }
            items(routines) {
                StartButton(navController, store, routine)
            }
        }


    }
}


@Preview
@Composable
fun PreviewRoutineDetail() {
    FitcenterTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            WorkoutDetails()
        }
    }
}
