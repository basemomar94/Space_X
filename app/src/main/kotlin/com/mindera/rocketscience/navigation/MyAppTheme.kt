package com.mindera.rocketscience.navigation

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.mindera.rocketscience.AppColors


@Composable
internal fun MyAppTheme(content: @Composable () -> Unit) {
    val colorScheme: ColorScheme = lightColorScheme(
        primary = AppColors.Primary,
        secondary = AppColors.Secondary,
        background = AppColors.Background,
        surface = AppColors.Surface,
        onPrimary = AppColors.OnPrimary,
        onSecondary = AppColors.OnSecondary,
        onBackground = AppColors.OnBackground,
        onSurface = AppColors.OnSurface
    )

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}

