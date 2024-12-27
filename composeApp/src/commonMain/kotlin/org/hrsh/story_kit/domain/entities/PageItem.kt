package org.hrsh.story_kit.domain.entities

sealed class PageItem(open val timeShow: Float = 5f) {
    data class Image(
        val imageUrl: String,
        val text: String,
        override val timeShow: Float = 5f
    ) : PageItem(timeShow)

    data class Video(
        val videoUrl: String,
        override val timeShow: Float = 5f
    ) : PageItem(timeShow)

    data class Question(
        val imageUrl: String,
        val question: String,
        val listAnswers: List<String>,
        override val timeShow: Float = 5f
    ) : PageItem(timeShow)

    data class Game(
        val name: String
    ) : PageItem()

    data object Error : PageItem()
}