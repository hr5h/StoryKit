package org.hrsh.story_kit.domain

sealed class PageItem {
    data class PageItemImage(
        val image: String,
        val text: String,
        val timeShow: Int = 5
    ) : PageItem()

    data class PageItemVideo(
        val video: String,
    ) : PageItem()

    data class PageItemQuestion(
        val image: String,
        val question: String,
        val listAnswers: List<String>
    ) : PageItem()

    data class PageItemGame(
        val name: String,
    ) : PageItem()

    data object PageItemError : PageItem()
}