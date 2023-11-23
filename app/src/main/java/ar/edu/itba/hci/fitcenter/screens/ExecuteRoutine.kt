package ar.edu.itba.hci.fitcenter.screens

import android.os.CountDownTimer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.itba.hci.fitcenter.R
import ar.edu.itba.hci.fitcenter.SampleData
import ar.edu.itba.hci.fitcenter.api.Models
import ar.edu.itba.hci.fitcenter.ui.theme.FitcenterTheme

@Composable
fun CountdownTimer(initialTimeSeconds: Int) {
    var currentTime by remember { mutableIntStateOf(initialTimeSeconds) }
    var isRunning by remember { mutableStateOf(false) }

    var timer: CountDownTimer? by remember { mutableStateOf(null) }

//    var timeInput by remember { mutableStateOf("") }

//    val density = LocalDensity.current.density

    fun startTimer() {
        timer = object : CountDownTimer(currentTime * 1000L, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                currentTime = (millisUntilFinished / 1000).toInt()
            }

            override fun onFinish() {
                isRunning = false
            }
        }
        timer?.start()
    }

    fun stopTimer() {
        timer?.cancel()
        timer = null
        isRunning = false
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // Visualización del tiempo restante
        Text(
            text = formatTime(currentTime),
            style = MaterialTheme.typography.displayMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        )

        //Spacer(modifier = Modifier.weight(1f))

        // Botones de control del temporizador
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    if (!isRunning) {
                        startTimer()
                        isRunning = true
                    }
                },
                enabled = !isRunning
            ) {
                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Start")
            }

            Button(
                onClick = {
                    stopTimer()
                },
                enabled = isRunning
            ) {
                Icon(imageVector = Icons.Default.Stop, contentDescription = "Stop")
            }

            Button(
                onClick = {
                    stopTimer()
                    currentTime = initialTimeSeconds
                },
                enabled = !isRunning
            ) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = "Reset")
            }
        }

    }
}

@Composable
fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return "%02d:%02d".format(minutes, remainingSeconds)
}


@Composable
fun Timer(seconds: Int, onExpire: () -> Unit) {
    Text(seconds.toString())
    Button(onClick = onExpire) {
        Text(stringResource(R.string.pause))
    }
}


@Composable
fun Execution(
    navController: NavController? = null,
    routine: Models.MegaRoutine = SampleData.megaRoutine,
    detailed: Boolean,
    lastPageOverride: Boolean = false
) {
    val cycles: List<Models.MegaCycle> = routine.megaCycles
    var currentCycle: Models.FullCycle? = cycles[0]
    var cycleExercises: List<Models.FullCycleExercise> = cycles[0].cycleExercises
    var cycleNum by remember { mutableIntStateOf(0) }
    var repNum by remember { mutableIntStateOf(0) }
    var exeNum by remember { mutableIntStateOf(0) }
    var currentExe: Models.FullCycleExercise = cycleExercises[0]
    var exerciseState by remember { mutableStateOf(currentExe) }
    var cycleState by remember { mutableStateOf(currentCycle) }
    var isLastPage by remember { mutableStateOf(lastPageOverride) }
    if(!isLastPage) {
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
            // Header
            Row {
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { navController?.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.Green,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }

            // Title
            cycleState?.let {
                Text(
                    text = it.name,
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Color.White
                    )
                )
            }
            Text(
                text = "${repNum + 1}/${cycleState?.repetitions}",
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
                modifier = Modifier.padding(top = 20.dp)
            )
            if(detailed){
            Text(
                text = exerciseState.exercise.detail.substring(0, 200),
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.White)
            )}
            Spacer(modifier = Modifier.weight(1f))


            // Nav
            Row(modifier = Modifier.padding(top = 200.dp)) {
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
                            modifier = Modifier.size(36.dp)
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
                        if ((repNum + 1).toLong() < cycleState?.repetitions!!) {
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
                        modifier = Modifier.size(36.dp)
                    )
                }
            }


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
    if(isLastPage){
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
                IconButton(onClick = { navController?.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.Green,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }

            Text(text = stringResource(R.string.congrats),
                style = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ),
                modifier = Modifier.padding(top = 20.dp))
            Text(text = stringResource(R.string.comple_workout),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ),
                modifier = Modifier.padding(top = 20.dp))
            Spacer(modifier = Modifier.weight(1f))


            // Nav
            Row(modifier = Modifier.padding(top = 200.dp)) {
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
                Spacer(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.weight(1f))

            Row( modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly){
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
                    onClick = { navController?.navigateUp() }
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
    }
}

@Composable
fun ExecutionPreview(detailed: Boolean, lastPageOverride: Boolean = false) {
    FitcenterTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Black
        ) {
            Execution(detailed = detailed, lastPageOverride = lastPageOverride)
        }
    }
}


@Preview
@Composable
fun SimpleExecutionPreview() {
    ExecutionPreview(detailed = false)
}

@Preview
@Composable
fun DetailedExecutionPreview() {
    ExecutionPreview(detailed = true)
}

@Preview
@Composable
fun ExecutionLastPagePreview() {
    ExecutionPreview(detailed = false, lastPageOverride = true)
}
