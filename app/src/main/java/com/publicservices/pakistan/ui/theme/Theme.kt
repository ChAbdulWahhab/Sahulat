package com.publicservices.pakistan.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = PakistanGreen,
    onPrimary = White,
    primaryContainer = PakistanGreen.copy(alpha = 0.08f),
    onPrimaryContainer = PakistanGreen,
    secondary = LightGreen,
    onSecondary = White,
    secondaryContainer = White,
    onSecondaryContainer = OnSurfaceLight,
    background = OffWhite,
    onBackground = OnSurfaceLight,
    surface = White,
    onSurface = OnSurfaceLight,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = OnSurfaceVariantLight,
    outline = LightGray,
    error = AccentRed,
    onError = White
)

private val DarkColorScheme = darkColorScheme(
    primary = LightGreen,
    onPrimary = DarkGreen,
    primaryContainer = PakistanGreen,
    onPrimaryContainer = OnSurfaceDark,
    secondary = LightGreen,
    onSecondary = DarkGreen,
    secondaryContainer = DarkGray,
    onSecondaryContainer = OnSurfaceDark,
    background = Black,
    onBackground = OnSurfaceDark,
    surface = DarkGray,
    onSurface = OnSurfaceDark,
    surfaceVariant = DarkGray,
    onSurfaceVariant = OnSurfaceVariantDark,
    outline = Gray,
    error = AccentRed,
    onError = White
)

@Composable
fun PublicServiceAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.surface.toArgb()
            val insetsController = WindowCompat.getInsetsController(window, view)
            insetsController.isAppearanceLightStatusBars = !darkTheme
            insetsController.isAppearanceLightNavigationBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
