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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import kotlinx.coroutines.launch


@Composable
fun UsernameField(
    value: String,
    onChange: (String) -> Unit,
    colors: TextFieldColors,
    enabled: Boolean,
    isError: Boolean,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current
    val leadingIcon = @Composable {
        Icon(
            Icons.Default.Person,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.secondary
        )
    }

    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
        leadingIcon = leadingIcon,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        label = { Text(stringResource(R.string.username)) },
        singleLine = true,
        visualTransformation = VisualTransformation.None,
        colors = colors,
        enabled = enabled,
        isError = isError
    )
}

@Composable
fun PasswordField(
    value: String,
    onChange: (String) -> Unit,
    onSubmit: () -> Unit,
    colors: TextFieldColors,
    enabled: Boolean,
    isError: Boolean,
    modifier: Modifier = Modifier,
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    val leadingIcon = @Composable {
        Icon(
            Icons.Default.Key,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.secondary
        )
    }
    val trailingIcon = @Composable {
        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
            Icon(
                if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.secondary
            )
        }
    }

    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password
        ),
        keyboardActions = KeyboardActions(
            onDone = { onSubmit() }
        ),
        label = { Text(stringResource(R.string.password)) },
        singleLine = true,
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        colors = colors,
        enabled = enabled,
        isError = isError
    )
}

suspend fun submit(
    store: Store? = null,
    navController: NavController? = null,
    username: String,
    password: String,
    loading: MutableState<Boolean>,
    error: MutableState<Exception?>
) {
    if (store != null && navController != null) {
        loading.value = true
        try {
            store.login(Models.Credentials(username = username, password = password))
        } catch (e: Exception) {
            error.value = e
            loading.value = false
        }
        navController.navigate("my-workouts") {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
        }
    }
}

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
            .padding(all = 28.dp)
            .width(276.dp),
    ) {
        val username = remember { mutableStateOf("") }
        val password = remember { mutableStateOf("") }

        val loading = remember { mutableStateOf(false) }
        val error = remember { mutableStateOf<Exception?>(null) }

        val scope = rememberCoroutineScope()
        val onSubmit: () -> Unit = {
            scope.launch {
                submit(
                    store = store,
                    navController = navController,
                    username = username.value,
                    password = password.value,
                    loading = loading,
                    error = error
                )
            }
        }

        val textFieldColors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.tertiary,
            unfocusedContainerColor = MaterialTheme.colorScheme.tertiary,
            disabledContainerColor = MaterialTheme.colorScheme.tertiary,
            errorContainerColor = MaterialTheme.colorScheme.tertiary,
            focusedBorderColor = MaterialTheme.colorScheme.secondary,
        )

        if (error.value != null) {
            Text(error.value?.message ?: "An unknown error has occurred")
        }
        UsernameField(
            value = username.value,
            onChange = { username.value = it },
            colors = textFieldColors,
            enabled = !loading.value,
            isError = error.value != null
        )
        PasswordField(
            value = password.value,
            onChange = { password.value = it },
            colors = textFieldColors,
            enabled = !loading.value,
            isError = error.value != null,
            onSubmit = onSubmit
        )
        Button(
            onClick = onSubmit,
            enabled = !loading.value
        ) {
            Text(stringResource(R.string.log_in))
        }
        if (loading.value) {
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
                .background(color = MaterialTheme.colorScheme.secondary)
                .safeDrawingPadding()
                .padding(bottom = 20.dp),
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
        Login()
    }
}
