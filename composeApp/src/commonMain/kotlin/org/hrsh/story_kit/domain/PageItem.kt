package org.hrsh.story_kit.domain

sealed class PageItem {
    data class PageItemImage(
        val imageUrl: String,
        val text: String,
        val timeShow: Int = 5
    ) : PageItem()

    data class PageItemVideo(
        val videoUrl: String,
    ) : PageItem()

    data class PageItemQuestion(
        val imageUrl: String,
        val question: String,
        val listAnswers: List<String>
    ) : PageItem()

    data class PageItemGame(
        val name: String,
    ) : PageItem()

    data object PageItemError : PageItem()
}