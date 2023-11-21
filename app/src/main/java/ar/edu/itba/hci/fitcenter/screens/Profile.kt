package ar.edu.itba.hci.fitcenter.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
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
        for (i in 1..3) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f) // reduce width here
                    .height(60.dp) // increase height here
                    .padding(horizontal = 10.dp, vertical = 10.dp)
                    .background(if (i == 1) MaterialTheme.colorScheme.primary else Color.White, shape = RoundedCornerShape(10.dp)) // change color for first rectangle
                    .border(if (i != 1) BorderStroke(1.dp, Color.Black) else BorderStroke(0.dp, Color.Transparent), RoundedCornerShape(10.dp)), // remove border for first rectangle
                contentAlignment = Alignment.Center // align content to center
            ) {
                when (i) {
                    1 -> Text("John Doe", fontWeight = FontWeight.Bold, fontSize = 15.sp) // make text bold and increase font size
                    2 -> Text("johndoe@email.com", fontSize = 15.sp) // increase font size
                    3 -> Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), // add padding here
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("User name", fontWeight = FontWeight.Bold, fontSize = 15.sp) // make text bold and increase font size
                        Text("johndoe", fontSize = 15.sp) // increase font size
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp)) // reduce height here
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
            Text("Logout", fontWeight = FontWeight.Bold, fontSize = 15.sp) // make text bold and increase font size
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