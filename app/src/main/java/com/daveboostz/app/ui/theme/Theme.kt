package com.daveboostz.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val Scheme = darkColorScheme(
    primary = NeonRed,
    onPrimary = PureBlack,
    background = PureBlack,
    onBackground = TextPrimary,
    surface = DarkSurface,
    onSurface = TextPrimary
)

@Composable
fun ThemeDaveBoostz(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = Scheme, typography = DaveTypography, content = content)
}
