package ar.edu.itba.hci.fitcenter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ar.edu.itba.hci.fitcenter.api.Models
import ar.edu.itba.hci.fitcenter.ui.theme.FitcenterTheme

class ExecuteRoutineActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FitcenterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Execution(detailed = false)
                }
            }
        }
    }
}

@Composable
fun Execution(routine: Models.MegaRoutine, detailed: Boolean) {
    Column {
        Text("Exit")
        Text("Cycle #/#")
        Text("Repetition #/#")
        Text("Exercise Name")
        Row {
            Text("Previous")
            Text("Next")
        }
        Text("Timer")
        Text("Pause")
    }
}

@Preview(showBackground = true)
@Composable
fun SimpleExecutionPreview() {
    FitcenterTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Execution(detailed = false)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailedExecutionPreview() {
    FitcenterTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Execution(detailed = true)
        }
    }
}
