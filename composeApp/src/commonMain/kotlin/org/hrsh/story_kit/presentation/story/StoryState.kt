package org.hrsh.story_kit.presentation.story

internal data class StoryState(
    val currentStory: Int = -1,
    val currentPage: List<Int> = emptyList(),
    val isShowStory: Boolean = false,
    val hasFirstStory: Boolean = false,
    //нужно ли открыть окно с favoriteStories после закрытия истории
    val isShowFavoriteStories: Boolean = false,
    //показываются ли favoriteStories
    val showFavoriteStories: Boolean = false,
    val storyColors: StoryColors = StoryColors()
)
