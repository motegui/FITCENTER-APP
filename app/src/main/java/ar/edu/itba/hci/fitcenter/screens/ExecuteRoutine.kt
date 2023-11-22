package ar.edu.itba.hci.fitcenter.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.itba.hci.fitcenter.R
import ar.edu.itba.hci.fitcenter.RoutineSampleData
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
fun Execution(navController: NavController? = null) {
    val cycles: Models.Cycles? = RoutineSampleData.cyclesRoutine
    var currentCycle: Models.FullCycle? = cycles?.content?.get(0)
    var cycleExercises: List<Models.FullCycleExercise> = RoutineSampleData.cylceInfo
    var cycleNum by remember { mutableIntStateOf(0) }
    var repNum by remember { mutableIntStateOf(0) }
    var exeNum by remember { mutableIntStateOf(0) }
    var currentExe: Models.FullCycleExercise = cycleExercises[0]
    var exerciseState by remember { mutableStateOf(currentExe) }
    var cycleState by remember { mutableStateOf(currentCycle) }

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
            Button(onClick = { navController?.navigate("workout") }) {
                Text(stringResource(R.string.quit))
            }
        }

        // Title

        cycleState?.let {
            Text(text = it.name,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color.White))
        }
        Text(text = "${repNum + 1}/${cycleState?.repetitions}",
                style = MaterialTheme.typography.titleLarge.copy(
                color = Color.White
                ))

        Text(text = exerciseState.exercise.name,
            style = MaterialTheme.typography.displayMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color.White
            ),
            modifier = Modifier.padding(top = 20.dp))
        Spacer(modifier = Modifier.weight(1f))


        // Nav
        Row {
            if(exeNum>0 || cycleNum>0 || repNum>0){
            IconButton(
                onClick = {
                    if(exeNum>0){
                        exeNum-=1;
                        currentExe =  cycleExercises[exeNum];
                        exerciseState = currentExe;
                        return@IconButton;
                    }
                    if((repNum).toLong() > 0){
                        repNum-=1;
                        exeNum=cycleExercises.size - 1;
                        currentExe =  cycleExercises[exeNum];
                        exerciseState = currentExe;
                        return@IconButton;
                    }
                    if(cycleNum>0){
                        cycleNum-=1;
                        currentCycle = cycles?.content?.get(cycleNum);
                        //cycleExercises = busco los nuevos ejercicios
                        cycleState = currentCycle;
                        exeNum=cycleExercises.size - 1;
                        currentExe =  cycleExercises[exeNum];
                        exerciseState = currentExe;
                    }
                }
            ){
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null, tint = Color.Green, modifier = Modifier.size(36.dp))}}
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = {
                    if(exeNum<cycleExercises.size - 1){
                        exeNum+=1;
                        currentExe =  cycleExercises[exeNum];
                        exerciseState = currentExe;
                        return@IconButton;
                    }
                    if((repNum+1).toLong() < cycleState?.repetitions!!){
                        repNum+=1;
                        exeNum=0;
                        currentExe =  cycleExercises[exeNum];
                        exerciseState = currentExe;
                        return@IconButton;
                    }
                    if(cycleNum< cycles?.totalCount!!-1){
                        cycleNum+=1;
                        currentCycle = cycles?.content?.get(cycleNum);
                        //cycleExercises = busco los nuevos ejercicios
                        cycleState = currentCycle;
                        repNum=0;
                        exeNum=0;
                        currentExe =  cycleExercises[exeNum];
                        exerciseState = currentExe;
                    }
                }
            ){
            Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null, tint = Color.Green, modifier = Modifier.size(36.dp))}
        }

        Spacer(modifier = Modifier.weight(1f))

        // Body
//        Timer(currentRep.duration) {
//
//        }
    }
}

@Composable
fun ExecutionPreview(detailed: Boolean, navController: NavController? = null) {
    FitcenterTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Black
        ) {
            Execution(navController)
        }
    }
}

@Preview(name = "Simple")
@Composable
fun SimpleExecutionPreview(navController: NavController? = null) {
    ExecutionPreview(detailed = false, navController)
}

@Preview(name = "Detailed")
@Composable
fun DetailedExecutionPreview(navController: NavController? = null) {
    ExecutionPreview(detailed = true, navController)
}
