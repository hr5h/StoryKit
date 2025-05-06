package org.hrsh.story_kit.di

import android.content.res.Resources
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
actual fun getScreenHeightDp(): Dp {
    return LocalConfiguration.current.screenHeightDp.dp
}

@Composable
actual fun getScreenWidthDp(): Dp {
    return LocalConfiguration.current.screenWidthDp.dp
}

@Composable
actual fun getFontScale(): Float {
    val metrics = Resources.getSystem().displayMetrics
    return metrics.scaledDensity / metrics.density
}