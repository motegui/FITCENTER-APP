package ar.edu.itba.hci.fitcenter.screens

import ar.edu.itba.hci.fitcenter.api.Store
import android.content.res.Configuration
import android.opengl.Visibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.itba.hci.fitcenter.R
import ar.edu.itba.hci.fitcenter.RoutineSampleData
import ar.edu.itba.hci.fitcenter.SortingCriterion
import ar.edu.itba.hci.fitcenter.api.Models
import ar.edu.itba.hci.fitcenter.navigate
import ar.edu.itba.hci.fitcenter.ui.theme.FitcenterTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import androidx.compose.foundation.layout.Row as Row

@Composable
fun DifficultyRating(difficulty: Models.Difficulty) {
    Row {
        repeat(3) { index ->
            val iconColor = if (index < difficulty.ordinal) {
                Color.Red
            } else {
                Color.Gray
            }
            androidx.compose.material3.Icon(
                imageVector = Icons.Default.LocalFireDepartment,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.padding(end = 4.dp)
            )
        }
    }
}

fun formatDate(dateValue: Long): String {
    val date = LocalDate.parse(dateValue.toString(), DateTimeFormatter.BASIC_ISO_DATE)
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy") // You can adjust the pattern as needed
    return date.format(formatter)
}

@Composable
fun Workout(navController: NavController? = null, store: Store? = null) {
    val routine: Models.FullRoutine? = RoutineSampleData.sportsRoutines.find { it.id == 1L }
    var isFavorite by remember { mutableStateOf(routine?.isFavorite) }
    var isEquipemntExpanded by remember { mutableStateOf(false) }
    var isDetailed by remember { mutableStateOf(false) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = Color.Gray, // Establecer el color a gris
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
                ){
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
                            isEquipemntExpanded = !isEquipemntExpanded
                        },
                        modifier = Modifier.align(Alignment.CenterVertically)
                    ) {
                        Icon(
                            imageVector = if (isEquipemntExpanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                            tint = Color.Black,
                            contentDescription = "Expand/Collapse",
                        )
                    }
                }
        }
        if(isEquipemntExpanded==true){
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
                    verticalAlignment = Alignment.CenterVertically){
                    Text(
                        text = "the equipment here",
                        style = MaterialTheme.typography.titleMedium)
                }
            }
        }

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
                modifier = Modifier.weight(1f)
                    .padding(top = 1.dp, start = 16.dp, end = 6.dp, bottom = 1.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column{
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

        Button(
            onClick = { navController?.navigate("workout") }
        ) {
            androidx.compose.material3.Text(stringResource(R.string.start))
        }

    }
}


@Composable
fun PreviewRoutineList(){
    FitcenterTheme{
        Surface(
            modifier= Modifier.fillMaxSize(),
            color= MaterialTheme.colorScheme.background,
        ){
            Workout()
        }
    }
}