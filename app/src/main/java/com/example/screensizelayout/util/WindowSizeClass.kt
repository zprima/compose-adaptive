package com.example.screensizelayout.util

import android.app.Activity
import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.toComposeRect
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.window.layout.WindowMetricsCalculator
import java.lang.IllegalArgumentException

/*
    You can have a data class WindowSizeInfo which has also the width and height info, and
    the WindowSize would then be the sealed object inside. But in this case, the official documentation
    said that in most cases the WindowSize is/can be determined by just looking at the width value.

    Also, we don't use LocalConfiguration.current.screenWidthDp/screenHeightDp as it returns
    values that do not include the system top bar. Not sure if that is good or bad, but the official
    docs says to use WindowMetrics.

    Why is it tied to Activity? Because WindowMetricsCalculator calculates off the Activity passed.

    getWindowSizeClass is public so that any option of getting DpSize (WindowMetrics, LocalConfiguration),
    can call it, and get the WindowSizeClass back.

    https://developer.android.com/jetpack/compose/layouts/adaptive
 */

enum class WindowSizeClass { Compact, Medium, Expanded }

@Composable
fun Activity.rememberWindowSizeClass() : WindowSizeClass {
    val windowSize = rememberWindowSize()

    val windowDpSize = with(LocalDensity.current){
        windowSize.toDpSize()
    }

    return getWindowSizeClass(windowDpSize)
}

@Composable
private fun Activity.rememberWindowSize(): Size {
    val configuration = LocalConfiguration.current

    val windowMetrics = remember(configuration){
        WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(this)
    }

    return windowMetrics.bounds.toComposeRect().size
}

@VisibleForTesting
fun getWindowSizeClass(windowDpSize: DpSize): WindowSizeClass = when {
    windowDpSize.width < 0.dp -> throw IllegalArgumentException("DP can't be negative")
    windowDpSize.width < 600.dp -> WindowSizeClass.Compact
    windowDpSize.width < 840.dp -> WindowSizeClass.Medium
    else -> WindowSizeClass.Expanded
}
