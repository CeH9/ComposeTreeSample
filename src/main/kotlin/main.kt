import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import core.Navigation

fun main() = Window(
    title = "Compose for Desktop",
    size = IntSize(600, 900),
    location = IntOffset(1700, 300),
    centered = false,
) {
    MaterialTheme(colors = darkColors()) {
        Surface(modifier = Modifier.fillMaxSize()) {
            Navigation.currentScreen()
        }
    }
}