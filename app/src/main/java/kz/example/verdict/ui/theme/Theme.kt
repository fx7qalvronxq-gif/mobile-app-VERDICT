package kz.example.verdict.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val VerdictColorScheme = darkColorScheme(
    primary = Gold,
    background = Background,
    surface = Surface,
    onPrimary = Background,
    onBackground = Cream,
    onSurface = Cream,
    outline = Border
)

@Composable
fun VerdictTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = VerdictColorScheme,
        typography = Typography,
        content = content
    )
}
