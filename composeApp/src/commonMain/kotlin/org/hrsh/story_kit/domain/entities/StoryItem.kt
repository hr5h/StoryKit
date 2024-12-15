package org.hrsh.story_kit.domain.entities

data class StoryItem(
    val id: Long,
    val imagePreview: String,
    val listPages: List<PageItem>,
    val isStartStory: Boolean = false,
    val isLike: Boolean = false,
    val countLike: Int = 0,
    val isFavorite: Boolean = false,
    val isViewed: Boolean = false
)