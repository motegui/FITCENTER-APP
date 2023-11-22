package ar.edu.itba.hci.fitcenter.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.itba.hci.fitcenter.RoutineSampleData
import ar.edu.itba.hci.fitcenter.api.Models
import ar.edu.itba.hci.fitcenter.ui.theme.FitcenterTheme


@Composable
fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current
    var isFocused by remember { mutableStateOf(false) }
    val leadingIcon = @Composable {
        Icon(
            Icons.Default.Search,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.secondary
        )
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChange(it)
                onSearch(it)
            },
            modifier = modifier
                .weight(1f)
                .heightIn(min = 56.dp)
                .onFocusChanged {
                    isFocused = it.isFocused
                },
            leadingIcon = leadingIcon,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch(value)
                    focusManager.clearFocus()
                }
            ),
            label = { Text("Search") },
            singleLine = true,
            visualTransformation = VisualTransformation.None,

        )
        // Cancel button
        if (isFocused) {
            TextButton(
                onClick = {
                    onValueChange("")
                    onSearch("")
                    focusManager.clearFocus()
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(text = "Cancel", color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Composable
fun RoutineSearchPortrait(routines: List<Models.FullRoutine>, navController: NavController? = null) {
    var searchQuery by remember { mutableStateOf("") }
    var filteredRoutines by remember { mutableStateOf(routines)}
    var sortingCriterion by remember { mutableStateOf(SortingCriterion.NAME) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        SearchBar(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
            },
            onSearch = { query ->
                filteredRoutines = if (query.isEmpty()) {
                    routines
                } else {
                    routines.filter { routine ->
                        routine.name.contains(query, ignoreCase = true)
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "Order by:",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .padding(horizontal = 8.dp)
        )
        SortingButtons(
            sortingCriterion = sortingCriterion,
            onSortingCriterionChanged = { newSortingCriterion ->
                sortingCriterion = newSortingCriterion
                filteredRoutines = polyvalentRoutineList(routines, sortingCriterion)
            }
        )
        HorizontalDivider(
            modifier = Modifier.padding(bottom = 12.dp),
            thickness = 2.dp,
            color = Color.LightGray
        )

        RoutineList(routines = filteredRoutines, navController = navController)
    }
}


@Composable
fun RoutineSearchLandscape(routines: List<Models.FullRoutine>, navController: NavController? = null) {
    var searchQuery by remember { mutableStateOf("") }
    var filteredRoutines by remember { mutableStateOf(routines) }
    var sortingCriterion by remember { mutableStateOf(SortingCriterion.NAME) }


        // Row for buttons and routine list
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            // Column for sorting buttons
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(top = 8.dp)
                    .weight(1f) // Take up available vertical space
            ) {
                // Search bar
                SearchBar(
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                    },
                    onSearch = { query ->
                        filteredRoutines = if (query.isEmpty()) {
                            routines
                        } else {
                            routines.filter { routine ->
                                routine.name.contains(query, ignoreCase = true)
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
                Text(
                    text = "Order by:",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .padding(horizontal = 12.dp)
                )
                VerticalSortingButtons(
                    sortingCriterion = sortingCriterion,
                    onSortingCriterionChanged = { newSortingCriterion ->
                        sortingCriterion = newSortingCriterion
                        filteredRoutines = polyvalentRoutineList(routines, sortingCriterion)
                    }
                )
            }

            // Vertical Divider
            VerticalDivider(
                modifier = Modifier.padding(horizontal = 12.dp),
                thickness = 2.dp,
                color = Color.LightGray
            )

            // Column for RoutineList
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxHeight()
                    .weight(2f)
            ) {
                RoutineList(routines = filteredRoutines, navController = navController)
            }
        }
    }

@Composable
fun RoutineSearch(routines: List<Models.FullRoutine>, navController: NavController? = null) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        RoutineSearchLandscape(routines = routines, navController = navController)
    } else {
        RoutineSearchPortrait(routines = routines, navController = navController)
    }
}
@Preview(name = "Light Mode")
@Composable
fun PreviewMenu() {
    FitcenterTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            RoutineSearch(polyvalentRoutineList(routines = RoutineSampleData.sportsRoutines, favorites = true))
        }
    }
}
