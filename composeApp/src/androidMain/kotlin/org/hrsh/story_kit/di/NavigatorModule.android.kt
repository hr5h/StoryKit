package org.hrsh.story_kit.di

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import dev.hrsh.story_kit.StoryActivity

actual class Navigator actual constructor() {
    @Composable
    actual fun navigateToDetail() {
        val androidContext = LocalContext.current
        val intent = Intent(androidContext, StoryActivity::class.java)
        androidContext.startActivity(intent)
    }
}