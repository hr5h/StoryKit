package org.hrsh.story_kit.di

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.awt.Toolkit

@Composable
actual fun getScreenHeightDp(): Dp {
    val screenSize = Toolkit.getDefaultToolkit().screenSize
    return (screenSize.height / Toolkit.getDefaultToolkit().screenResolution).dp
}

@Composable
actual fun getScreenWidthDp(): Dp {
    val screenSize = Toolkit.getDefaultToolkit().screenSize
    return (screenSize.width / Toolkit.getDefaultToolkit().screenResolution).dp
}

@Composable
actual fun getFontScale(): Float {
    TODO("Not yet implemented")
}