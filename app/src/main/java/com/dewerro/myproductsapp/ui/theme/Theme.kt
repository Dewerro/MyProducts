package com.dewerro.myproductsapp.ui.theme

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.LocalOverScrollConfiguration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    // main color
    primary = SoftPurple,
    primaryVariant = Purple,
    // background color
    background = BlackPurple,
    // used in text and icons
    secondary = Color.White,
    secondaryVariant = Crimson,
    // used in snackbar
    surface = BrightPurple,
    onPrimary = Color.Transparent
)

private val LightColorPalette = lightColors(
    // main color
    primary = LightBlue,
    primaryVariant = LightBlue,
    // background color
    background = PaleBlue,
    // used in text and icons
    secondary = Blue,
    secondaryVariant = Blue,
    // used in snackbar
    surface = BrightPink,
    onPrimary = Color.Transparent
)

@ExperimentalFoundationApi
@Composable
fun MyProductsTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors
    ) {
        CompositionLocalProvider(
            LocalOverScrollConfiguration provides null,
            content = content
        )
    }
}