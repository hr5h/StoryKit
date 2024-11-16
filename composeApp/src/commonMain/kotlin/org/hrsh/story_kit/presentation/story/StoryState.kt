package org.hrsh.story_kit.presentation.story

data class StoryState(
    val currentStory: Int? = null,
    val currentPage: List<Int> = emptyList()
)
