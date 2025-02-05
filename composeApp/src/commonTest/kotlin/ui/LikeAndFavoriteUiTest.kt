package ui

import org.hrsh.story_kit.domain.entities.StoryItem
import kotlin.test.Test
import kotlin.test.assertEquals

fun likeButtonClicked(storyItem: StoryItem): StoryItem {
    return storyItem.copy(
        countLike = if (storyItem.isLike) storyItem.countLike - 1 else storyItem.countLike + 1,
        isLike = !storyItem.isLike
    )
}

fun favoriteButtonClicked(storyItem: StoryItem): StoryItem {
    return storyItem.copy(
        isFavorite = !storyItem.isFavorite
    )
}

class LikeAndFavoriteLogicTest {

    @Test
    fun testLikeButtonIncrementsLikeCount() {
        val storyItem = StoryItem(
            id = 1L,
            imagePreview = "http://example.com/image.jpg",
            listPages = emptyList(),
            isStartStory = false,
            isLike = false,
            countLike = 0,
            isFavorite = false,
            isViewed = false
        )

        val updatedStoryItem = likeButtonClicked(storyItem)

        assertEquals(1, updatedStoryItem.countLike)
        assertEquals(true, updatedStoryItem.isLike)
    }

    @Test
    fun testLikeButtonDecrementsLikeCount() {
        val storyItem = StoryItem(
            id = 1L,
            imagePreview = "http://example.com/image.jpg",
            listPages = emptyList(),
            isStartStory = false,
            isLike = true,
            countLike = 1,
            isFavorite = false,
            isViewed = false
        )

        val updatedStoryItem = likeButtonClicked(storyItem)

        assertEquals(0, updatedStoryItem.countLike)
        assertEquals(false, updatedStoryItem.isLike)
    }

    @Test
    fun testFavoriteButtonTogglesFavoriteStatus() {
        val storyItem = StoryItem(
            id = 1L,
            imagePreview = "http://example.com/image.jpg",
            listPages = emptyList(),
            isStartStory = false,
            isLike = false,
            countLike = 0,
            isFavorite = false,
            isViewed = false
        )

        val updatedStoryItem = favoriteButtonClicked(storyItem)

        assertEquals(true, updatedStoryItem.isFavorite)
    }

    @Test
    fun testFavoriteButtonTogglesFromFavorite() {
        val storyItem = StoryItem(
            id = 1L,
            imagePreview = "http://example.com/image.jpg",
            listPages = emptyList(),
            isStartStory = false,
            isLike = false,
            countLike = 0,
            isFavorite = true,
            isViewed = false
        )

        val updatedStoryItem = favoriteButtonClicked(storyItem)

        assertEquals(false, updatedStoryItem.isFavorite)
    }
}

fun main() {
    val testInstance = LikeAndFavoriteLogicTest()
    testInstance.testLikeButtonIncrementsLikeCount()
    testInstance.testLikeButtonDecrementsLikeCount()
    testInstance.testFavoriteButtonTogglesFavoriteStatus()
    testInstance.testFavoriteButtonTogglesFromFavorite()
}