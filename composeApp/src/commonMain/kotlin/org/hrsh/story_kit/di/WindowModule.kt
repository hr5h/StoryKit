package org.hrsh.story_kit.di

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp

@Composable
expect fun getScreenHeightDp(): Dp

@Composable
expect fun getScreenWidthDp(): Dp

@Composable
expect fun getFontScale(): Float