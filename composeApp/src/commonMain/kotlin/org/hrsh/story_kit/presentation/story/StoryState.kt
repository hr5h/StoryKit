package org.hrsh.story_kit.presentation.story

data class StoryState(
    val currentStory: Int = -1,
    val currentPage: List<Int> = emptyList(),
    val isShowStory: Boolean = false,
    val hasFirstStory: Boolean = false
)
