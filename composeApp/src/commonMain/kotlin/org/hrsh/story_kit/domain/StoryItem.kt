package org.hrsh.story_kit.domain

data class StoryItem(
    val imagePreview: String,
    val listPages: List<PageItem>,
    val showOnStart: Boolean
)