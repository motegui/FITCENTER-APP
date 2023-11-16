package ar.edu.itba.hci.fitcenter.screens

import ar.edu.itba.hci.fitcenter.R
import ar.edu.itba.hci.fitcenter.api.Models
import ar.edu.itba.hci.fitcenter.api.Store
import ar.edu.itba.hci.fitcenter.ui.theme.FitcenterTheme
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import ar.edu.itba.hci.fitcenter.Screen
import kotlinx.coroutines.launch


// TODO: Read this guide for improving this form's UX
// https://medium.com/@WhiteBatCodes/simple-login-page-in-jetpack-compose-9c92af690234

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginForm(navController: NavController? = null, store: Store? = null) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .background(
                color = Color(0x800C0D0D),
                shape = RoundedCornerShape(size = 10.dp)
            )
            .padding(all = 28.dp),
    ) {
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        var loading by remember { mutableStateOf(false) }
        var error: Exception? by remember { mutableStateOf(null) }

        if (error != null) {
            Text(error.toString())
        }

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(stringResource(R.string.username)) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                focusedBorderColor = MaterialTheme.colorScheme.secondary

            ),
            enabled = !loading
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(R.string.password)) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = MaterialTheme.colorScheme.tertiary
            ),
            enabled = !loading
        )
        val scope = rememberCoroutineScope()
        Button(
            onClick = {
                if (store != null && navController != null) {
                    loading = true
                    scope.launch {
                        try {
                            store.login(Models.Credentials(username = username, password = password))
                        } catch (e: Exception) {
                            error = e
                            return@launch
                        }
                        navController.navigate(Screen.MyWorkouts.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                        }
                    }
                    loading = false
                }
            },
            enabled = !loading
        ) {
            Text(stringResource(R.string.log_in))
        }
        if (loading) {
            CircularProgressIndicator()
        }
    }
}


@Composable
fun Login(navController: NavController? = null, store: Store? = null) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(R.drawable.landing2),
                contentScale = ContentScale.Crop
            ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(color = MaterialTheme.colorScheme.secondary),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(R.string.app_name),
                color = MaterialTheme.colorScheme.primary,
                letterSpacing = 11.sp,
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp,
            )
        }
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        LoginForm(navController, store)
    }
}


@Preview(name="Light Mode")
@Preview(
    name="Dark Mode",
    showBackground=true,
    uiMode=Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewLogin() {
    FitcenterTheme {
        // TO DO: use sample store or modify Login to partially work without a store
        Login()
    }
}
