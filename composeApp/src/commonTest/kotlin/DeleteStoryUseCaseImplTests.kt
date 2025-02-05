package org.hrsh.story_kit.domain.usecases

import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import org.hrsh.story_kit.domain.entities.PageItem
import org.hrsh.story_kit.domain.entities.StoryItem
import org.hrsh.story_kit.domain.repositories.TestStoryRepository

class DeleteStoryUseCaseImplTests {

    private lateinit var storyRepository: TestStoryRepository
    private lateinit var deleteStoryUseCase: DeleteStoryUseCaseImpl

    @BeforeTest
    fun setUp() {
        storyRepository = TestStoryRepository()
        deleteStoryUseCase = DeleteStoryUseCaseImpl(storyRepository)
    }

    @Test
    fun testDeleteStory() = runBlocking {
        val storyItem = createTestStoryItem()
        storyRepository.postStory(storyItem)
        assertTrue(storyRepository.stories.contains(storyItem), "Story should be in the repository before deletion!")
        deleteStoryUseCase(storyItem)
        assertFalse(storyRepository.stories.contains(storyItem), "Story should be removed from the repository!")
    }

    @Test
    fun testDeleteNonExistentStory() = runBlocking {
        val nonExistentStoryItem = createTestStoryItem().copy(id = 999)
        deleteStoryUseCase(nonExistentStoryItem)
        assertFalse(storyRepository.stories.any { it.id == nonExistentStoryItem.id }, "Story should not exist in the repository!")
    }

    @Test
    fun testDeleteStoryMultipleTimes() = runBlocking {
        val storyItem = createTestStoryItem()
        storyRepository.postStory(storyItem)

        deleteStoryUseCase(storyItem)
        assertFalse(storyRepository.stories.contains(storyItem), "Story should be removed from the repository!")

        deleteStoryUseCase(storyItem)
        assertFalse(storyRepository.stories.contains(storyItem), "Story should still be removed from the repository!")
    }

    private fun createTestStoryItem(): StoryItem {
        return StoryItem(
            id = 200,
            imagePreview = "https://example.com/image_preview.jpg",
            listPages = listOf(
                PageItem.Image(imageUrl = "https://example.com/page1.jpg", text = "Page 1"),
                PageItem.Image(imageUrl = "https://example.com/page2.jpg", text = "Page 2")
            )
        )
    }
}