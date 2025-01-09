package com.skyvo.mobile.core.uikit.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun AppPrimaryTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    isNavigationBarVisible: Boolean = true,
    navigationBarColor: Color? = null,
    isTransparentBackground: Boolean = false,
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    val colorScheme = if (darkTheme) {
        AppDarkColors
    } else {
        AppLightColors
    }

    if (darkTheme) {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = false
        )
        systemUiController.setNavigationBarColor(
            color = navigationBarColor ?: colorScheme.colorSurfaceBase
        )
    } else {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = true
        )
        systemUiController.setNavigationBarColor(
            color = navigationBarColor ?: colorScheme.colorSurfaceBase
        )
    }
    systemUiController.isNavigationBarVisible = isNavigationBarVisible

    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(
            primary = colorScheme.primary,
            onPrimary = colorScheme.onPrimary,
            surface = colorScheme.surface,
            onSurface = colorScheme.onSurface,
            background = colorScheme.background,
            onBackground = colorScheme.onBackground,
            secondary = colorScheme.secondary,
        )
    ) {
        AppCoreColorTheme(
            colors = colorScheme
        ) {
            Surface(
                color = if (isTransparentBackground) Color.Transparent else colorScheme.colorSurfaceBase,
                content = content
            )
        }
    }
}

@Composable
fun AppCoreColorTheme(
    colors: AppCoreColorScheme,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalAppColor provides colors,
    ) {
        content()
    }
}