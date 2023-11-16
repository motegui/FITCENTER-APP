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
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

fun formatDate(dateValue: Int): String {
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
fun RoutineCard(rt: Models.FullRoutine){
    Row(modifier= Modifier
        .padding(all = 8.dp)
        .fillMaxWidth()
    ){
        // Keep track of whether the Routine is expanded or not
        var isExpanded by remember { mutableStateOf(false) }

        //Toggle the variable when this column is clicked
        Column(modifier= Modifier
            .clickable { isExpanded = !isExpanded }
            .fillMaxWidth()
        ){
            Surface(
                shape= MaterialTheme.shapes.medium,
                shadowElevation=4.dp,
                modifier= Modifier
                    .animateContentSize()
                    .padding(4.dp)
                    .fillMaxWidth()
            ){
                Column(modifier= Modifier.padding(12.dp)){
                    Text(
                        text=rt.name,
                        style= MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier= Modifier.padding(bottom=6.dp))
                    if (isExpanded) {
                        Text(
                            text="Clicking should open a new window with routine details"
                        )
                    } else {
                        Text(
                            text = formatDate(rt.date),
                            style = MaterialTheme.typography.bodySmall ,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                        Row {
                            DifficultyRating(difficulty = rt.difficulty)
                            Text(
                                text= rt.category.name,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(start=6.dp)
                            )
                        }
                    }
                }
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

@Composable
fun SortedRoutineList(routines: List<Models.FullRoutine>, sortingCriterion: SortingCriterion) {
    val sortedRoutines = when (sortingCriterion) {
        SortingCriterion.DATE -> routines.sortedByDescending { it.date }
        SortingCriterion.SCORE -> routines.sortedByDescending { it.score }
        SortingCriterion.DIFFICULTY -> routines.sortedBy { it.difficulty.ordinal }
        SortingCriterion.CATEGORY -> routines.sortedBy { it.category.name }
    }
    RoutineList(sortedRoutines)
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
            SortedRoutineList(routines = RoutineSampleData.sportsRoutines, sortingCriterion = SortingCriterion.DATE)
        }
    }
}
