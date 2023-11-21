package ar.edu.itba.hci.fitcenter.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ar.edu.itba.hci.fitcenter.SampleData
import ar.edu.itba.hci.fitcenter.api.Models
import ar.edu.itba.hci.fitcenter.ui.theme.FitcenterTheme


@Composable
fun Execution(routine: Models.MegaRoutine, detailed: Boolean) {
    Column {
        // Header
        Row {
            Text("Exit")
        }

        // Title
        Text("Cycle #/${routine.megaCycles.size}")
        Text("Repetition #/#")
        Text(routine.name)

        // Nav
        Row {
            Text("Previous")
            Text("Next")
        }

        // Body
        Text("Timer")
        Text("Pause")
    }
}

@Composable
fun ExecutionPreview(detailed: Boolean) {
    FitcenterTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Execution(SampleData.megaRoutine, detailed)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SimpleExecutionPreview() {
    ExecutionPreview(detailed = false)
}

@Preview(showBackground = true)
@Composable
fun DetailedExecutionPreview() {
    ExecutionPreview(detailed = true)
}
