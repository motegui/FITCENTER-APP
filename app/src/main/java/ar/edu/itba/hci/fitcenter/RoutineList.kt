package ar.edu.itba.hci.fitcenter

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
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import ar.edu.itba.hci.fitcenter.api.Models
import ar.edu.itba.hci.fitcenter.ui.theme.FitcenterTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun formatDate(dateValue: Long): String {
    val date = LocalDate.parse(dateValue.toString(), DateTimeFormatter.BASIC_ISO_DATE)
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy") // You can adjust the pattern as needed
    return date.format(formatter)
}

@Composable
fun DifficultyRating(difficulty: Models.Difficulty) {
    Row {
        repeat(3) { index ->
            val iconColor = if (index < difficulty.ordinal) {
                Color.Red
            } else {
                Color.Gray
            }
            Icon(
                imageVector = Icons.Default.LocalFireDepartment,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.padding(end = 4.dp)
            )
        }
    }
}

@Composable
fun RoutineCard(rt: Models.FullRoutine) {
    var isFavorite by remember { mutableStateOf(rt.isFavorite) }
    var isExpanded by remember { mutableStateOf(false) }

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
                .clickable { isExpanded = !isExpanded }
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
                            text= rt.category.name,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(start=6.dp)
                        )
                    }

                if (isExpanded) {
                    Text(
                        text = "Clicking should open a new window with routine details"
                    )
                }
            }

            // Heart icon
            IconButton(
                onClick = {
                    isFavorite = !isFavorite
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
    Column(modifier= Modifier.padding(6.dp)) {
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
fun RoutineList(routines: List<Models.FullRoutine>){
    LazyColumn{
        items(routines){routine ->
            RoutineCard(routine)
        }
    }
}
fun polyvalentRoutineList(
    routines: List<Models.FullRoutine>,
    sortingCriterion: SortingCriterion = SortingCriterion.NAME,
    favorites: Boolean = false
): List<Models.FullRoutine> {
    var myRoutines = routines
    if(favorites){
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

@Preview(name="Light Mode")
@Preview(
    name="Dark Mode",
    showBackground=true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewRoutineList(){
    FitcenterTheme{
        Surface(
            modifier= Modifier.fillMaxSize(),
            color= MaterialTheme.colorScheme.background,
        ){
            RoutineList(polyvalentRoutineList(routines = RoutineSampleData.sportsRoutines, favorites = true))
        }
    }
}
