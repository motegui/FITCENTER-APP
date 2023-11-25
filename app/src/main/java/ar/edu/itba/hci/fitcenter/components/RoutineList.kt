package ar.edu.itba.hci.fitcenter.components

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ar.edu.itba.hci.fitcenter.R
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
    store: Store? = null,
    tablet: Boolean? = false,
    onCardClick: (Long) -> Unit
) {
    //var isFavorite by remember { mutableStateOf(rt.isFavorite) }
    var clicked by remember { mutableStateOf(false) }

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
                    if(tablet == true){
                        onCardClick(rt.id)
                        return@clickable
                    }
                    if (store == null || clicked) return@clickable
                    clicked = true
                    val dest = "workout-details/${rt.id}"
                    if (navController?.currentDestination?.route == dest) return@clickable
                    navController?.navigate(dest)
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

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (rt.metadataCategory != null) {
                        Text(
                            text = rt.metadataCategory!!,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }

            }
            DifficultyRating(difficulty = rt.difficulty)
            // Heart icon
//            IconButton(
//                onClick = {
//                    isFavorite = !isFavorite
//                    scope.launch {
//                        store?.setFavorite(rt.id, isFavorite)
//                    }
//                },
//                modifier = Modifier.align(Alignment.CenterVertically)
//            ) {
//                Icon(
//                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
//                    tint = if (isFavorite) Color(0xFFFF7F7F) else Color.Gray,
//                    contentDescription = "Favorite"
//                )
//            }
        }
    }
}


enum class SortingCriterion {
    NAME,
    DATE,
    DIFFICULTY,
    CATEGORY
}

@Composable
fun RoutineList(
    routines: List<Models.FullRoutine>,
    navController: NavController? = null,
    store: Store? = null,
    tablet: Boolean? = false,
    onCardClick: (Long) -> Unit
) {
    if (routines.isNotEmpty()) {
        LazyColumn {
            items(routines) { routine ->
                RoutineCard(routine, navController, store, tablet, onCardClick)
            }
        }
    } else {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = stringResource(R.string.empty_favorites),
                fontStyle = FontStyle.Italic,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
            TextButton(onClick = { navController?.navigate("find-workouts") }) {
                Text(
                    text = "${stringResource(R.string.find_workouts)}...",
                    fontStyle = FontStyle.Italic,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

fun filterRoutineList(
    routines: List<Models.FullRoutine>,
    sortingCriterion: SortingCriterion = SortingCriterion.NAME,
    searchQuery: String = "",
    favorites: Boolean = false
): List<Models.FullRoutine> {
    if (routines.isEmpty()) return routines
    val filtered = if (favorites) {
        routines.filter { it.isFavorite }
    } else {
        routines.map { it }  // Copy the list
    }
    val sorted = when (sortingCriterion) {
        SortingCriterion.NAME -> filtered.sortedBy { it.name }
        SortingCriterion.DATE -> filtered.sortedByDescending { it.date }
        SortingCriterion.DIFFICULTY -> filtered.sortedBy { it.difficulty.ordinal }
        SortingCriterion.CATEGORY -> filtered.sortedBy { it.metadataCategory }
    }
    return if (searchQuery.isEmpty()) {
        sorted
    } else {
        sorted.filter { it.name.contains(searchQuery, ignoreCase = true) }
    }
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
                filterRoutineList(
                    routines = RoutineSampleData.sportsRoutines,
                    favorites = false,
                    searchQuery = "",
                ),
                onCardClick = {}
            )
        }
    }
}
