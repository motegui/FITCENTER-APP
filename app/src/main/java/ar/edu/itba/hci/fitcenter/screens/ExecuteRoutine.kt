package ar.edu.itba.hci.fitcenter.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.itba.hci.fitcenter.R
import ar.edu.itba.hci.fitcenter.SampleData
import ar.edu.itba.hci.fitcenter.api.Models
import ar.edu.itba.hci.fitcenter.ui.theme.FitcenterTheme


@Composable
fun Timer(seconds: Int, onExpire: () -> Unit) {
    Text(seconds.toString())
    Button(onClick = onExpire) {
        Text(stringResource(R.string.pause))
    }
}


@Composable
fun Execution(routine: Models.MegaRoutine, detailed: Boolean) {
    var cycleNum by remember { mutableIntStateOf(0) }
    var repNum by remember { mutableIntStateOf(0) }
    val currentCycle by remember { derivedStateOf { routine.megaCycles[cycleNum] } }
    val currentRep by remember { derivedStateOf { currentCycle.cycleExercises[repNum] } }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(
                top = 36.dp,
                start = 48.dp,
                end   = 48.dp,
                bottom = 84.dp
            )
    ) {
        // Header
        Row {
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = {}) {
                Text(stringResource(R.string.quit))
            }
        }

        // Title
        Text("Cycle ${cycleNum + 1}/${routine.megaCycles.size}")
        Text("Repetition ${repNum + 1}/${currentCycle.repetitions}")
        Text(currentCycle.name)

        Spacer(modifier = Modifier.weight(1f))

        // Nav
        Row {
            Text("Previous")
            Spacer(modifier = Modifier.weight(1f))
            Text("Next")
        }

        Spacer(modifier = Modifier.weight(1f))

        // Body
        Timer(currentRep.duration) {

        }
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

@Preview(name = "Simple")
@Composable
fun SimpleExecutionPreview() {
    ExecutionPreview(detailed = false)
}

@Preview(name = "Detailed")
@Composable
fun DetailedExecutionPreview() {
    ExecutionPreview(detailed = true)
}
