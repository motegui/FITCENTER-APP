package ar.edu.itba.hci.fitcenter.screens

import android.os.CountDownTimer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.itba.hci.fitcenter.R
import ar.edu.itba.hci.fitcenter.SampleData
import ar.edu.itba.hci.fitcenter.api.Models
import ar.edu.itba.hci.fitcenter.ui.theme.FitcenterTheme



@Composable
fun ExecutionL(
    navController: NavController? = null,
    megaRoutine: Models.MegaRoutine = SampleData.megaRoutine,
    detailed: Boolean,
    lastPageOverride: Boolean = false
) {
    val cycles: List<Models.MegaCycle> = megaRoutine.megaCycles
    var currentCycle: Models.FullCycle = cycles[0]
    var cycleExercises: List<Models.FullCycleExercise> = cycles[0].cycleExercises
    var cycleNum by remember { mutableIntStateOf(0) }
    var repNum by remember { mutableIntStateOf(0) }
    var exeNum by remember { mutableIntStateOf(0) }
    var currentExe: Models.FullCycleExercise = cycleExercises[0]
    var exerciseState by remember { mutableStateOf(currentExe) }
    var cycleState by remember { mutableStateOf(currentCycle) }
    var isLastPage by remember { mutableStateOf(lastPageOverride) }
    Surface(color = Color.Black) {
        if (!isLastPage) {
            // Header
            Row {
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { navController?.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.Green,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
            Row(){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(
                            top = 36.dp,
                            start = 48.dp,
                            end = 48.dp,
                            bottom = 84.dp
                        )
                ) {
                    // Title
                    Text(
                        text = cycleState.name,
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Color.White
                        )
                    )
                    Text(
                        text = "${repNum + 1}/${cycleState.repetitions}",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Color.White
                        )
                    )

                    Text(
                        text = exerciseState.exercise.name,
                        style = MaterialTheme.typography.displayMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .fillMaxWidth()
                            .wrapContentHeight()
                    )

                    if (detailed) {
                        Text(
                            text = exerciseState.exercise.detail.substring(0, 200),
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = Color.White
                            ),
                            textAlign = TextAlign.Justify,
                            modifier = Modifier
                                .padding(top = 20.dp)
                                .fillMaxWidth()
                                .wrapContentHeight()
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(
                                top = 36.dp,
                                start = 48.dp,
                                end = 48.dp,
                                bottom = 84.dp
                            )
                    ) {
                        if (exerciseState.repetitions > 0) {
                            Text(
                                text = "x${exerciseState.repetitions}",
                                style = MaterialTheme.typography.displayMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                ),
                                modifier = Modifier.padding(top = 20.dp)
                            )
                        }

                        if (exerciseState.duration > 0) {
                            CountdownTimer(initialTimeSeconds = exerciseState.duration)
                        }
                    }
            }

                Spacer(modifier = Modifier.weight(1f))


                // Nav
                // TODO deal with left and right
                Row() {
                    if (exeNum > 0 || cycleNum > 0 || repNum > 0) {
                        IconButton(
                            onClick = {
                                if (exeNum > 0) {
                                    exeNum -= 1;
                                    currentExe = cycleExercises[exeNum];
                                    exerciseState = currentExe;
                                    return@IconButton;
                                }
                                if ((repNum).toLong() > 0) {
                                    repNum -= 1;
                                    exeNum = cycleExercises.size - 1;
                                    currentExe = cycleExercises[exeNum];
                                    exerciseState = currentExe;
                                    return@IconButton;
                                }
                                if (cycleNum > 0) {
                                    cycleNum -= 1;
                                    currentCycle = cycles[cycleNum];
                                    cycleExercises = cycles[cycleNum].cycleExercises;
                                    cycleState = currentCycle;
                                    exeNum = cycleExercises.size - 1;
                                    currentExe = cycleExercises[exeNum];
                                    exerciseState = currentExe;
                                    return@IconButton;
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null,
                                tint = Color.Green,
                                modifier = Modifier.size(45.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = {
                            if (exeNum < cycleExercises.size - 1) {
                                exeNum += 1;
                                currentExe = cycleExercises[exeNum];
                                exerciseState = currentExe;
                                return@IconButton;
                            }
                            if ((repNum + 1).toLong() < cycleState.repetitions) {
                                repNum += 1;
                                exeNum = 0;
                                currentExe = cycleExercises[exeNum];
                                exerciseState = currentExe;
                                return@IconButton;
                            }
                            if (cycleNum < cycles.size - 1) {
                                cycleNum += 1;
                                currentCycle = cycles[cycleNum];
                                cycleExercises = cycles[cycleNum].cycleExercises;
                                cycleState = currentCycle;
                                repNum = 0;
                                exeNum = 0;
                                currentExe = cycleExercises[exeNum];
                                exerciseState = currentExe;
                                return@IconButton;
                            }
                            isLastPage = true;
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = null,
                            tint = Color.Green,
                            modifier = Modifier.size(45.dp)
                        )
                    }
                }


            }
        }
        if (isLastPage) {
            Row(){
                Column(modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(8.dp),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    // Nav

                    IconButton(
                        onClick = {
                            isLastPage = false
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = Color.Green,
                            modifier = Modifier.size(36.dp)
                        )
                    }

                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(
                            40.dp
                        )
                        .weight(4f)
                ) {
                    Spacer(modifier = Modifier.weight(1f))

                    // Header

                    Text(
                        text = stringResource(R.string.congrats),
                        style = MaterialTheme.typography.displaySmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        ),
                        modifier = Modifier.padding(top = 20.dp)
                    )
                    Text(
                        text = stringResource(R.string.comple_workout),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        ),
                        modifier = Modifier.padding(vertical = 20.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = {
                                currentCycle = cycles[0]
                                cycleExercises = cycles[0].cycleExercises
                                cycleNum = 0
                                repNum = 0
                                exeNum = 0
                                currentExe = cycleExercises[0]
                                exerciseState = currentExe
                                cycleState = currentCycle
                                isLastPage = false
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Restart",
                                tint = Color.Black,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                        Button(
                            onClick = { navController?.popBackStack() }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ExitToApp,
                                contentDescription = "Exit",
                                tint = Color.Black,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))


                }
                Column(
                    modifier=Modifier.weight(1f).padding(12.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    IconButton(onClick = { navController?.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color.Green,
                            modifier = Modifier.size(36.dp)
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun ExecutionLPreview(detailed: Boolean, lastPageOverride: Boolean = false) {
    FitcenterTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Black
        ) {
            ExecutionL(detailed = detailed, lastPageOverride = lastPageOverride)
        }
    }
}


@Preview
@Composable
fun SimpleExecutionLPreview() {
    ExecutionLPreview(detailed = false)
}

@Preview
@Composable
fun DetailedExecutionLPreview() {
    ExecutionLPreview(detailed = true)
}

@Preview
@Composable
fun ExecutionLastPageLPreview() {
    ExecutionLPreview(detailed = false, lastPageOverride = true)
}
