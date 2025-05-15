package org.hrsh.story_kit.domain.usecases

import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.assertFalse
import org.hrsh.story_kit.domain.entities.PageItem
import org.hrsh.story_kit.domain.entities.StoryItem
import org.hrsh.story_kit.domain.repositories.TestStoryRepository

class DeleteAllStoryUseCaseImplTests {

    private lateinit var storyRepository: TestStoryRepository
    private lateinit var deleteAllStoryUseCase: DeleteAllStoryUseCaseImpl

    @BeforeTest
    fun setUp() {
        storyRepository = TestStoryRepository()
        deleteAllStoryUseCase = DeleteAllStoryUseCaseImpl(storyRepository)
    }

    @Test
    fun testDeleteAllStories() = runBlocking {
        val storyItem1 = createTestStoryItem(1L)
        val storyItem2 = createTestStoryItem(2L)

        storyRepository.insertStory(storyItem1)
        storyRepository.insertStory(storyItem2)
        assertTrue(storyRepository.stories.contains(storyItem1), "StoryItem1 should be in the repository!")
        assertTrue(storyRepository.stories.contains(storyItem2), "StoryItem2 should be in the repository!")
        deleteAllStoryUseCase()
        assertFalse(storyRepository.stories.contains(storyItem1), "StoryItem1 should be removed from the repository!")
        assertFalse(storyRepository.stories.contains(storyItem2), "StoryItem2 should be removed from the repository!")
    }

    @Test
    fun testDeleteAllStoriesWhenEmpty() = runBlocking {
        deleteAllStoryUseCase()
        assertTrue(storyRepository.stories.isEmpty(), "Repository should still be empty after delete attempt!")
    }

    private fun createTestStoryItem(id: Long): StoryItem {
        return StoryItem(
            id = id,
            imagePreview = "https://example.com/image_preview_$id.jpg",
            listPages = listOf(
                PageItem.Image(imageUrl = "https://example.com/page1_$id.jpg", text = "Page 1"),
                PageItem.Image(imageUrl = "https://example.com/page2_$id.jpg", text = "Page 2")
            )
        )
    }
}