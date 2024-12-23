package org.hrsh.story_kit.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.hrsh.story_kit.di.Koin
import org.hrsh.story_kit.domain.interfaces.StoryManager
import org.hrsh.story_kit.presentation.story.StoryViewModel

@Composable
fun StoryKit(color: Color) {
    MiniatureStories(color)
}

object StoryKit {
    val storyManager = (Koin.di?.koin?.get<StoryViewModel>()!!) as StoryManager
}