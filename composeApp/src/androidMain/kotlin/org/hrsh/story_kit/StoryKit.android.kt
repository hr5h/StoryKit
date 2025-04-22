package org.hrsh.story_kit

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import org.hrsh.story_kit.di.Koin
import org.hrsh.story_kit.domain.interfaces.StoryManager
import org.hrsh.story_kit.presentation.story.StoryKit
import org.koin.android.ext.koin.androidContext

@Composable
fun storyKit(): StoryManager {
    val context = LocalContext.current
    Koin.setupKoin {
        androidContext(context)
    }
    return StoryKit()
}