package org.hrsh.story_kit.di

import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

actual class BackHandler actual constructor(
    private val enabled: Boolean,
    private val onBack: () -> Unit
) {
    private var callback: OnBackPressedCallback? = null

    @Composable
    actual fun setup() {
        val activity = LocalContext.current as ComponentActivity
        callback = object : OnBackPressedCallback(enabled) {
            override fun handleOnBackPressed() {
                onBack()
            }
        }.apply {
            activity.onBackPressedDispatcher.addCallback(this)
        }
    }

    actual fun dispose() {
        callback?.remove()
    }
}