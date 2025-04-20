package org.hrsh.story_kit.di

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