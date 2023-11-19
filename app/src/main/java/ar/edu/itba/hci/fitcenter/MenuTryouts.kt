package ar.edu.itba.hci.fitcenter

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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.itba.hci.fitcenter.ui.theme.FitcenterTheme


@OptIn(ExperimentalMaterial3Api::class)
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
fun MyScreen() {
    val myRoutines = RoutineSampleData.sportsRoutines
    var searchQuery by remember { mutableStateOf("") }
    var filteredRoutines by remember { mutableStateOf(myRoutines) }

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
                    myRoutines
                } else {
                    myRoutines.filter { routine ->
                        routine.name.contains(query, ignoreCase = true)
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
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
            MyScreen()
        }
    }
}
