package ar.edu.itba.hci.fitcenter.screens

import ar.edu.itba.hci.fitcenter.api.Store
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import ar.edu.itba.hci.fitcenter.ui.theme.FitcenterTheme


@Composable
fun Profile(store: Store) {
    Text("This is the profile screen")
}


@Preview(name="Light Mode")
@Preview(
    name="Dark Mode",
    showBackground=true,
    uiMode=Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewProfile() {
    FitcenterTheme {
        Profile()
    }
}
