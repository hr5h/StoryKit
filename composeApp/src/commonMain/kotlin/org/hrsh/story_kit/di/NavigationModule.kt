package org.hrsh.story_kit.di

import androidx.compose.runtime.Composable

expect class BackHandler(enabled: Boolean, onBack: () -> Unit) {
    @Composable
    fun setup()
    fun dispose()
}