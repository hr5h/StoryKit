package org.hrsh.story_kit.di

import android.app.ActivityOptions
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import dev.hrsh.story_kit.R
import dev.hrsh.story_kit.StoryActivity

actual class Navigator actual constructor() {

    @Composable
    actual fun navigateToStory() {
        val androidContext = LocalContext.current
        val intent = Intent(androidContext, StoryActivity::class.java)
        val options = ActivityOptions.makeCustomAnimation(androidContext, R.anim.scale_in, R.anim.scale_out).toBundle()
        androidContext.startActivity(intent, options)
    }

    actual fun finishStory() {
        (StoryActivity.context as StoryActivity).finish()
    }
}