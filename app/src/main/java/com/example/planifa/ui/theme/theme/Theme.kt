package com.example.planifa.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

// Définition des palettes de couleurs pour le mode clair et le mode sombre
private val DarkColorPalette = darkColorScheme(
    primary = Purple200,
    secondary = Purple700,
    tertiary = Teal200
)

private val LightColorPalette = lightColorScheme(
    primary = Purple500,
    secondary = Purple700,
    tertiary = Teal200
    // Vous pouvez personnaliser d'autres couleurs par défaut (background, surface, etc.) ici si besoin.
)

@Composable
fun PlanifaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorPalette else LightColorPalette

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Assurez-vous d'avoir défini votre objet Typography
        content = content
    )
}
