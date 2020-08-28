package io.github.rsookram.compose.example.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkPalette = darkColors(
    primary = Color(0xFFFFFFFF),
    primaryVariant = Color(0xFFE0E0E0),
    secondary = Color(0xFF757575),
)

private val LightPalette = lightColors(
    primary = Color(0xFF121212),
    primaryVariant = Color(0xFF000000),
    secondary = Color(0xFFE0E0E0),
    secondaryVariant = Color(0xFFE0E0E0),
)

@Composable
fun AppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    MaterialTheme(
        if (darkTheme) DarkPalette else LightPalette,
        content = content
    )
}
