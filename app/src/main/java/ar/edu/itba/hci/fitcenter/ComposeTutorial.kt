package ar.edu.itba.hci.fitcenter

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.itba.hci.fitcenter.ui.theme.FitcenterTheme

object ComposeTutorial {
    data class Message(
        val author: String,
        val body: String
    )

    @Composable
    fun MessageCard(msg: Message) {
        Row(modifier= Modifier.padding(all=8.dp)) {
            Image(
                painter= painterResource(R.drawable.avatar_placeholder),
                contentDescription="Contact profile picture",
                modifier= Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colorScheme.primary),
            )

            Spacer(modifier= Modifier.width(8.dp))

            // Keep track of whether the message is expanded or not
            var isExpanded by remember { mutableStateOf(false) }
            // Gradually update surfaceColor from one color to another
            val surfaceColor by animateColorAsState(
                if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                label="guh animation",
            )

            // Toggle the variable when this column is clicked
            Column(modifier= Modifier.clickable { isExpanded = !isExpanded }) {
                Text(
                    text=msg.author,
                    style= MaterialTheme.typography.titleSmall
                )
                Spacer(modifier= Modifier.width(4.dp))

                Surface(
                    shape= MaterialTheme.shapes.medium,
                    shadowElevation=2.dp,
                    // surfaceColor changes the surface's color gradually
                    color=surfaceColor,
                    // animateContentSize changes the surface's size gradually
                    modifier= Modifier.animateContentSize().padding(1.dp)
                ) {
                    Text(
                        text=msg.body,
                        modifier= Modifier.padding(all=4.dp),
                        // If the message is expanded, display all of its contents.
                        // Otherwise, display only the first line.
                        maxLines=if (isExpanded) Int.MAX_VALUE else 1,
                        style= MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }

    @Composable
    fun Conversation(messages: List<Message>) {
        LazyColumn {
            items(messages) { message ->
                MessageCard(message)
            }
        }
    }
}


//@Preview(name="Light Mode")
//@Preview(
//    name="Dark Mode",
//    showBackground=true,
//    uiMode=Configuration.UI_MODE_NIGHT_YES
//)
//@Composable
//fun PreviewMessageCard() {
//    FitcenterTheme {
//        Surface {
//            MessageCard(
//                msg=Message(
//                    "Billy Mays",
//                    "Hi, Billy Mays here for the Big City Slider Station! The fast and easy way to press and cook delicious sliders — those restaurant mini-burgers everyone loves!"
//                )
//            )
//        }
//    }
//}

@Preview(name="Light Mode")
@Preview(
    name="Dark Mode",
    showBackground=true,
    uiMode= Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewConversation() {
    FitcenterTheme {
        Surface(
            modifier= Modifier.fillMaxSize(),
            color= MaterialTheme.colorScheme.background,
        ) {
            ComposeTutorial.Conversation(SampleData.conversationSample)
        }
    }
}