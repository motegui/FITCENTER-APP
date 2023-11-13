package ar.edu.itba.hci.fitcenter.screens

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import ar.edu.itba.hci.fitcenter.api.Store
import ar.edu.itba.hci.fitcenter.ui.theme.FitcenterTheme



@Composable
fun FindWorkouts(store: Store) {
    Text("This is the FindWorkouts screen")
}


@Preview(name="Light Mode")
@Preview(
    name="Dark Mode",
    showBackground=true,
    uiMode=Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewFindWorkouts() {
    FitcenterTheme {
        FindWorkouts()
    }
}
