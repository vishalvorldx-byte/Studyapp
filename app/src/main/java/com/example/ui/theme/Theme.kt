package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val EduDXColorScheme = darkColorScheme(
    primary = OrangePrimary,
    onPrimary = OrangeOnPrimary,
    primaryContainer = OrangePrimaryContainer,
    onPrimaryContainer = Color.White,
    secondary = CyanSecondary,
    onSecondary = CyanOnSecondary,
    secondaryContainer = CyanSecondaryContainer,
    onSecondaryContainer = Color.White,
    tertiary = PeriwinkleTertiary,
    onTertiary = Color(0xFF071747),
    tertiaryContainer = PeriwinkleTertiaryContainer,
    onTertiaryContainer = Color(0xFF222F5F),
    background = DarkBackground,
    onBackground = DarkOnSurface,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    surfaceVariant = DarkSurfaceContainerHighest,
    onSurfaceVariant = DarkOnSurfaceVariant,
    outline = BorderOutline,
    outlineVariant = BorderOutlineVariant,
    surfaceContainerLowest = DarkSurfaceContainerLowest,
    surfaceContainerLow = DarkSurfaceContainerLow,
    surfaceContainer = DarkSurfaceContainer,
    surfaceContainerHigh = DarkSurfaceContainerHigh,
    surfaceContainerHighest = DarkSurfaceContainerHighest,
    surfaceBright = DarkSurfaceBright
)

@Composable
fun EduDXTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = EduDXColorScheme,
        typography = Typography,
        content = content
    )
}
