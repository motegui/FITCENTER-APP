package ar.edu.itba.hci.fitcenter.components

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.itba.hci.fitcenter.R
import ar.edu.itba.hci.fitcenter.RoutineSampleData
import ar.edu.itba.hci.fitcenter.api.Models
import ar.edu.itba.hci.fitcenter.api.Store
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

    val trailingIcon = @Composable {
        IconButton(onClick = {
            onValueChange("")
            onSearch("")
            focusManager.clearFocus()
        }) {
            Icon(
                Icons.Default.Cancel,
                contentDescription = "Cancel",
                tint = MaterialTheme.colorScheme.secondary
            )
        }
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
            trailingIcon = { if (value.isNotEmpty()) trailingIcon() },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch(value)
                    focusManager.clearFocus()
                }
            ),
            label = { Text(stringResource(R.string.search)) },
            singleLine = true,
            visualTransformation = VisualTransformation.None,

        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun RoutineSearchPortrait(
    routines: List<Models.FullRoutine>,
    navController: NavController? = null,
    store: Store? = null,
    favorites: Boolean,
    tablet: Boolean? = false,
    onCardClick: (Int) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var sortingCriterion by remember { mutableStateOf(SortingCriterion.NAME) }
//    val filteredRoutines by remember {
//        derivedStateOf {
//            filterRoutineList(
//                routines = routines,
//                sortingCriterion = sortingCriterion,
//                searchQuery = searchQuery,
//                favorites = favorites
//            )
//        }
//    }
    val filteredRoutines by derivedStateOf {
        filterRoutineList(
            routines = routines,
            sortingCriterion = sortingCriterion,
            searchQuery = searchQuery,
            favorites = favorites
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .padding(horizontal = 8.dp)
    ) {
        SearchBar(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            onSearch = {},
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "${stringResource(R.string.order_by)}:",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .padding(horizontal = 8.dp)
        )
        SortingButtons(
            sortingCriterion = sortingCriterion,
            onSortingCriterionChanged = { sortingCriterion = it }
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            thickness = 2.dp,
            color = Color.LightGray
        )
        RoutineList(
            routines = filteredRoutines,
            navController = navController,
            store = store,
            tablet = tablet,
            onCardClick = onCardClick
        )
    }
}


@SuppressLint("UnrememberedMutableState")
@Composable
fun RoutineSearchLandscape(
    routines: List<Models.FullRoutine>,
    navController: NavController? = null,
    store: Store? = null,
    favorites: Boolean
) {
    var searchQuery by remember { mutableStateOf("") }
    var sortingCriterion by remember { mutableStateOf(SortingCriterion.NAME) }
//    val filteredRoutines by remember {
//        derivedStateOf {
//            filterRoutineList(
//                routines = routines,
//                sortingCriterion = sortingCriterion,
//                searchQuery = searchQuery,
//                favorites = favorites
//            )
//        }
//    }

    val filteredRoutines by derivedStateOf {
        filterRoutineList(
            routines = routines,
            sortingCriterion = sortingCriterion,
            searchQuery = searchQuery,
            favorites = favorites
        )
    }

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
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                onSearch = {}
            )
            Text(
                text = stringResource(R.string.order_by),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .padding(horizontal = 12.dp)
            )
            VerticalSortingButtons(
                sortingCriterion = sortingCriterion,
                onSortingCriterionChanged = { sortingCriterion = it }
            )
        }

        Divider(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .fillMaxHeight()
                .width(2.dp), // Adjust the width of the divider as needed
            color = Color.LightGray // You can set the color of the divider
        )

        // Column for RoutineList
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxHeight()
                .weight(2f)
        ) {
            RoutineList(
                routines = filteredRoutines,
                navController = navController,
                store = store,
                tablet = false,
                onCardClick = {}
            )
        }
    }
}

@Composable
fun RoutineSearch(
    routines: List<Models.FullRoutine>,
    navController: NavController? = null,
    store: Store? = null,
    favorites: Boolean
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        RoutineSearchLandscape(routines, navController, store, favorites)
    } else {
        RoutineSearchPortrait(routines, navController, store, favorites, false, {})
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
            RoutineSearch(RoutineSampleData.sportsRoutines, favorites = false)
        }
    }
}
