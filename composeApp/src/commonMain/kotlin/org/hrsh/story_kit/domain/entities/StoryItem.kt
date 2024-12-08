package org.hrsh.story_kit.domain.entities

data class StoryItem(
    val imagePreview: String,
    val listPages: List<PageItem>,
    val showOnStart: Boolean,
    val isLike: Boolean,
    val countLike: Int,
    val isFavorite: Boolean
)