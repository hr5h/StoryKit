package org.hrsh.story_kit.domain

sealed class PageItem {
    data class Image(
        val imageUrl: String,
        val text: String,
        val timeShow: Int = 5
    ) : PageItem()

    data class Video(
        val videoUrl: String,
    ) : PageItem()

    data class Question(
        val imageUrl: String,
        val question: String,
        val listAnswers: List<String>
    ) : PageItem()

    data class Game(
        val name: String,
    ) : PageItem()

    data object Error : PageItem()
}