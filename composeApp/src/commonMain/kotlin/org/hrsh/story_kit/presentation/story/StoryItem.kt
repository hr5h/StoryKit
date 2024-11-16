package org.hrsh.story_kit.presentation.story

import org.hrsh.story_kit.presentation.page.PageItem

data class StoryItem(
    val imagePreview: String,
    val listPages: List<PageItem>,
    val showOnStart: Boolean
)