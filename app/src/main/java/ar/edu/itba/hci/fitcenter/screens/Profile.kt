package ar.edu.itba.hci.fitcenter.screens

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
@Composable
fun Profile(navController: NavController? = null, store: Store? = null) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Default.AccountCircle,
            contentDescription = "User Icon",
            modifier = Modifier.size(100.dp)
        )

        // Add four rectangles with rounded corners
        for (i in 1..4) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f) // reduce width here
                    .height(50.dp)
                    .padding(horizontal = 10.dp, vertical = 10.dp)
                    .background(Color.White, shape = RoundedCornerShape(10.dp))
                    .border(BorderStroke(1.dp, Color.Black), RoundedCornerShape(10.dp))
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Add logout button
        Button(
            onClick = { /*TODO: Handle logout*/ },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.5f),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colorScheme.primary) // change the color here
        ) {
            Text("Logout")
        }
    }
}

@Preview(name="Light Mode")
@Composable
fun PreviewProfile() {
    MaterialTheme {
        Profile()
    }
}