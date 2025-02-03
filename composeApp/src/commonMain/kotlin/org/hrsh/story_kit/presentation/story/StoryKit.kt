package org.hrsh.story_kit.presentation.story

import androidx.compose.runtime.Composable
import org.hrsh.story_kit.di.Koin
import org.hrsh.story_kit.domain.interfaces.StoryManager
import org.hrsh.story_kit.presentation.MiniatureStories

@Composable
fun StoryKit(colors: StoryColors = StoryColors()) {
    MiniatureStories(colors)
}

object StoryKit {
    val storyManager = (Koin.di?.koin?.get<StoryViewModel>()!!) as StoryManager
}