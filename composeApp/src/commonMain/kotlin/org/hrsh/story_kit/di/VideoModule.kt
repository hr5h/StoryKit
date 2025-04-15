package org.hrsh.story_kit.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier

@Composable
internal expect fun VideoPlayer(modifier: Modifier, url: String, isVisible: Boolean, isAnimate: MutableState<Boolean>)