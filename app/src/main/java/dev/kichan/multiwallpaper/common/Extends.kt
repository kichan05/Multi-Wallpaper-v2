package dev.kichan.multiwallpaper.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Int.pxToDp() : Float {
    val density = LocalDensity.current.density

    return this / density
}

@Composable
fun Float.pxToDp() : Float {
    val density = LocalDensity.current.density

    return this / density
}
