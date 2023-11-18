package ar.edu.itba.hci.fitcenter.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = Green,
    secondary = ReallyDarkGray,
    tertiary = Color.White,

    onPrimary = Color.Black,
    onSecondary = Color.White,
    onTertiary = Color.Black,

    // Other default colors:
    // tertiary = ReallyDarkGray,
    // background = Color(0xFFFFFBFE),
    // surface = Color(0xFFFFFBFE),
    // onTertiary = Color.White,
    // onBackground = Color(0xFF1C1B1F),
    // onSurface = Color(0xFF1C1B1F),
)

private val DarkColorScheme = LightColorScheme


//private val DarkColorScheme = darkColorScheme(
//    primary = Green,
//    secondary = ReallyDarkGray,
//    tertiary = ReallyDarkGray,
//
//    onPrimary = Color.Black,
//    onSecondary = Color.White,
//    onTertiary = Color.White,
//)


@Composable
fun FitcenterTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    val window = (view.context as Activity).window
    SideEffect {
        window.statusBarColor = Color.Transparent.toArgb()
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
    }

    MaterialTheme(
        colorScheme=colorScheme,
        typography=Typography,
        content=content
    )
}
