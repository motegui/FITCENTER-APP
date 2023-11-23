package ar.edu.itba.hci.fitcenter.screens

import android.graphics.drawable.Drawable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.itba.hci.fitcenter.api.Store
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import ar.edu.itba.hci.fitcenter.Placeholder
import ar.edu.itba.hci.fitcenter.R
import ar.edu.itba.hci.fitcenter.ui.theme.FitcenterTheme
import coil.request.ImageRequest
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

@Composable
private fun Avatar(imageUrl: String? = null) {
    Surface(
        modifier = Modifier.size(200.dp),
        shape = CircleShape
    ) {
        if (imageUrl != null) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                placeholder = rememberVectorPainter(Icons.Default.AccountCircle),
                contentDescription = "Avatar",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            Icon(
                Icons.Default.AccountCircle,
                contentDescription = "Avatar",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun Profile(navController: NavController? = null, store: Store? = null) {
    var user by remember { mutableStateOf(Placeholder.emptyUser) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(store) {
        if (store != null) {
            user = store.currentUser()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 28.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Avatar(user.avatarUrl)

        // Add four rectangles with rounded corners
        for (i in 1..3) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f) // reduce width here
                    .height(60.dp) // increase height here
                    .padding(horizontal = 10.dp, vertical = 10.dp)
                    .background(
                        if (i == 1) MaterialTheme.colorScheme.primary else Color.White,
                        shape = RoundedCornerShape(10.dp)
                    ) // change color for first rectangle
                    .border(
                        if (i != 1) BorderStroke(1.dp, Color.Black) else BorderStroke(
                            0.dp,
                            Color.Transparent
                        ), RoundedCornerShape(10.dp)
                    ), // remove border for first rectangle
                contentAlignment = Alignment.Center // align content to center
            ) {
                when (i) {
                    1 -> Text("${user.firstName} ${user.lastName}", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    2 -> Text(user.email, fontSize = 15.sp)
                    3 -> Text("@${user.username}", fontSize = 15.sp)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Logout button
        Button(
            onClick = {
                scope.launch {
                    store?.logout()
                    navController?.navigate("login") {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                    }
                }
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.5f),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(stringResource(R.string.log_out), fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }
    }
}

@Preview(name="Light Mode")
@Composable
fun PreviewProfile() {
    FitcenterTheme {
        Surface(
            modifier=Modifier.fillMaxSize(),
            color=MaterialTheme.colorScheme.background,
        ) {
            Profile()
        }
    }
}
