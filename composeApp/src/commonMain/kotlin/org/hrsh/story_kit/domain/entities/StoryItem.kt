package org.hrsh.story_kit.domain.entities

data class StoryItem(
    /**
     * the id of your story for further tracking of events like, view, skip
     */
    val id: Long,
    /**
     * the image that will be shown in the story miniature
     */
    val imagePreview: String,
    /**
     * a list of pages that have a history
     */
    val listPages: List<PageItem>,
    val isStartStory: Boolean = false,
    val isLike: Boolean = false,
    val countLike: Int = 0,
    val isFavorite: Boolean = false,
    val isViewed: Boolean = false
)