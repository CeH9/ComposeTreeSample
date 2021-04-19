package core

import androidx.compose.runtime.Composable
import features.home.ui.HomeView

/**
 * Mock Navigation
 */
object Navigation {
    @Composable
    fun currentScreen() = HomeView(ViewModelProvider().provide())
}
