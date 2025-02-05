import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import org.hrsh.story_kit.domain.entities.PageItem
import org.hrsh.story_kit.domain.entities.StoryItem
import org.hrsh.story_kit.domain.usecases.UpdateStoryUseCaseImpl
import org.hrsh.story_kit.domain.repositories.TestStoryRepository
class UpdateStoryUseCaseImplTests {

    private lateinit var storyRepository: TestStoryRepository
    private lateinit var updateStoryUseCase: UpdateStoryUseCaseImpl

    @BeforeTest
    fun setUp() {
        storyRepository = TestStoryRepository()
        updateStoryUseCase = UpdateStoryUseCaseImpl(storyRepository)
    }

    @Test
    fun testUpdateStory() = runBlocking {
        val originalStoryItem = createTestStoryItem()
        storyRepository.postStory(originalStoryItem)
        val updatedStoryItem = originalStoryItem.copy(imagePreview = "new_image_url", id = originalStoryItem.id)
        updateStoryUseCase(updatedStoryItem)
        val updatedInRepository = storyRepository.stories.find { it.id == originalStoryItem.id }
        assertEquals(updatedStoryItem.imagePreview, updatedInRepository?.imagePreview, "Story was not updated successfully!")
    }

    @Test
    fun testUpdateNonExistentStory() = runBlocking {
        val nonExistentStoryItem = createTestStoryItem().copy(id = 999)
        updateStoryUseCase(nonExistentStoryItem)
        assertFalse(storyRepository.stories.any { it.id == nonExistentStoryItem.id }, "Story should not exist in the repository!")
    }

    @Test
    fun testUpdateStoryWithoutChange() = runBlocking {
        val originalStoryItem = createTestStoryItem()
        storyRepository.postStory(originalStoryItem)
        updateStoryUseCase(originalStoryItem)

        val updatedInRepository = storyRepository.stories.find { it.id == originalStoryItem.id }
        assertEquals(originalStoryItem, updatedInRepository, "Story should remain unchanged!")
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