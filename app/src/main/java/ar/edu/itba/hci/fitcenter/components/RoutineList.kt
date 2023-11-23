package ar.edu.itba.hci.fitcenter.components

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.itba.hci.fitcenter.RoutineSampleData
import ar.edu.itba.hci.fitcenter.api.Models
import ar.edu.itba.hci.fitcenter.api.Store
import ar.edu.itba.hci.fitcenter.ui.theme.FitcenterTheme
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch


@Composable
fun RoutineCard(
    rt: Models.FullRoutine,
    navController: NavController? = null,
    store: Store? = null
) {
    var isFavorite by remember { mutableStateOf(rt.isFavorite) }

    val scope = rememberCoroutineScope()

    Surface(
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 4.dp,
        modifier = Modifier
            .animateContentSize()
            .padding(4.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .clickable {
                    if (store == null) return@clickable
                    scope.launch {
                        val gson = GsonBuilder().create()
                        // Promote FullRoutine to MegaRoutine to receive cycle and exercise info
                        val megaRoutine = Models.MegaRoutine(store, rt)
                        val megaRoutineJson = gson.toJson(megaRoutine)
                        navController?.navigate(
                            "workout-details/?megaRoutineJson={megaRoutineJson}"
                                .replace(
                                    oldValue = "{megaRoutineJson}",
                                    newValue = megaRoutineJson
                                )
                        )
                    }
                }
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Routine Information
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(
                    text = rt.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = formatDate(rt.date),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )

                // Difficulty below the date, with category
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DifficultyRating(difficulty = rt.difficulty)
                    Text(
                        text = rt.category.name,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 6.dp)
                    )
                }

            }

            // Heart icon
            IconButton(
                onClick = {
                    isFavorite = !isFavorite
                    scope.launch {
                        store?.setFavorite(rt.id, isFavorite)
                    }
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    tint = if (isFavorite) Color(0xFFFF7F7F) else Color.Gray,
                    contentDescription = "Favorite"
                )
            }
        }
    }
}


//Not used because of changes, but for now kept because could be partly re-used elsewhere
@Composable
fun ExerciseDetails(exercise: List<Models.FullExercise>) {
    Column(modifier = Modifier.padding(6.dp)) {
        for (e in exercise) {
            Text(text = e.name, style = MaterialTheme.typography.titleMedium)
            Text(buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Description: ")
                }
                append(e.detail)
            }, style = MaterialTheme.typography.bodyMedium)
            Text(buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Type: ")
                }
                append(e.type.toString())
            }, style = MaterialTheme.typography.bodyMedium)

            Text(buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Duration: ")
                }
                append(
                    e.duration.toString()
                )
            }, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

enum class SortingCriterion {
    NAME,
    DATE,
    SCORE,
    DIFFICULTY,
    CATEGORY
}

@Composable
fun RoutineList(
    routines: List<Models.FullRoutine>,
    navController: NavController? = null,
    store: Store? = null
) {
    LazyColumn {
        items(routines) { routine ->
            RoutineCard(routine, navController, store)
        }
    }
}

fun polyvalentRoutineList(
    routines: List<Models.FullRoutine>,
    sortingCriterion: SortingCriterion = SortingCriterion.NAME,
    favorites: Boolean = false
): List<Models.FullRoutine> {
    var myRoutines = routines
    if (favorites) {
        myRoutines = routines.filter { it.isFavorite }
    }
    val sortedRoutines = when (sortingCriterion) {
        SortingCriterion.NAME -> myRoutines.sortedBy { it.name }
        SortingCriterion.DATE -> myRoutines.sortedByDescending { it.date }
        SortingCriterion.SCORE -> myRoutines.sortedByDescending { it.score }
        SortingCriterion.DIFFICULTY -> myRoutines.sortedBy { it.difficulty.ordinal }
        SortingCriterion.CATEGORY -> myRoutines.sortedBy { it.category.name }
    }
    return sortedRoutines
}

@Preview(name = "Light Mode")
@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewRoutineList() {
    FitcenterTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            RoutineList(
                polyvalentRoutineList(
                    routines = RoutineSampleData.sportsRoutines,
                    favorites = true
                )
            )
        }
    }
}
