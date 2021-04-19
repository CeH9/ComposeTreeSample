package core

import androidx.compose.runtime.compositionLocalOf
import features.home.ui.HomeViewModel

object CompositionKeys {
    val ViewModel = compositionLocalOf<HomeViewModel> { error("ViewModel is undefined!") }
}