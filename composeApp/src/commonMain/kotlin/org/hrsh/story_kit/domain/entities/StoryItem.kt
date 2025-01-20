package org.hrsh.story_kit.domain.entities

/**
 * Represents a story item in the application.
 *
 * @property id A unique identifier for the story. It is used to access, add, delete,
 *               or modify the story in the database. This key is also utilized for
 *               receiving events related to this ID.
 * @property imagePreview A string that holds the URL or path to the image that is
 *                        displayed in the stories feed.
 * @property listPages A list of [PageItem] that will be shown when the story is opened.
 * @property isStartStory A boolean flag indicating whether the story should be opened
 *                        upon app launch.
 * @property isLike A boolean variable indicating whether the story has been liked.
 * @property countLike An integer representing the number of likes the story has received.
 * @property isFavorite A boolean variable indicating whether the story has been added
 *                      to favorites.
 * @property isViewed A boolean variable indicating whether the story has been viewed.
 */
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