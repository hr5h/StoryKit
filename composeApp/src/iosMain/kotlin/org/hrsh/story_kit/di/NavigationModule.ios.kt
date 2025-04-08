package org.hrsh.story_kit.di

import androidx.compose.runtime.Composable

actual class BackHandler actual constructor(
    private val enabled: Boolean,
    private val onBack: () -> Unit
) {
    @Composable
    actual fun setup() {
        println("BackHandler setup for iOS")
    }

    actual fun dispose() {
        println("BackHandler disposed for iOS")
    }
}