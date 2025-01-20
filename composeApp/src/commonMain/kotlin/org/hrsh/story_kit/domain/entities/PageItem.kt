package org.hrsh.story_kit.domain.entities

/**
 * Represents a page item that can be part of a story.
 * This sealed class can have different types of page items.
 */
sealed class PageItem {

    /**
     * Represents an image page item.
     *
     * @property imageUrl A string representing the URL of the image that will be displayed
     *                    as the main content.
     * @property text A string that contains text to be shown at the bottom of the image.
     * @property timeShow An integer representing the duration (in seconds) for which the
     *                    story will be displayed.
     */
    data class Image(
        val imageUrl: String,
        val text: String,
        val timeShow: Int = 5
    ) : PageItem()

    /**
     * Represents a video page item.
     *
     * @property videoUrl A string representing the URL of the video that will be displayed.
     */
    data class Video(
        val videoUrl: String,
    ) : PageItem()

    /**
     * Represents a question page item.
     *
     * @property imageUrl A string representing the URL of the image associated with the question.
     * @property question A string containing the question that will be displayed.
     * @property listAnswers A list of strings representing the possible answers that can be selected.
     */
    data class Question(
        val imageUrl: String,
        val question: String,
        val listAnswers: List<String>
    ) : PageItem()

    /**
     * Represents a game page item.
     *
     * @property name A string representing the name of the game.
     */
    data class Game(
        val name: String,
    ) : PageItem()

    /**
     * Represents an error page item.
     * This object signifies that an error has occurred.
     */
    data object Error : PageItem()
}