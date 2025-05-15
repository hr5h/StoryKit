import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.flow.toList
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse
import org.hrsh.story_kit.domain.entities.PageItem
import org.hrsh.story_kit.domain.entities.StoryItem
import org.hrsh.story_kit.domain.usecases.InsertStoryUseCaseImpl
import org.hrsh.story_kit.domain.repositories.TestStoryRepository

class InsertStoryUseCaseImplTests {

    private lateinit var storyRepository: TestStoryRepository
    private lateinit var insertStoryUseCase: InsertStoryUseCaseImpl

    @BeforeTest
    fun setUp() {
        storyRepository = TestStoryRepository()
        insertStoryUseCase = InsertStoryUseCaseImpl(storyRepository)
    }

    @Test
    fun testInsertStory() = runBlocking {
        val storyItem = createTestStoryItem()
        insertStoryUseCase(storyItem)

        assertEquals(storyItem, storyRepository.postedStory, "Story was not posted correctly!")
    }
    @Test
    fun testPostStory() = runBlocking {
        val storyItem = createTestStoryItem()
        storyRepository.insertStory(storyItem)

        assertEquals(storyItem, storyRepository.stories.first(), "Story was not posted correctly!")
        assertEquals(1, storyRepository.stories.size, "Expected 1 story in the repository!")
    }

    @Test
    fun testDeleteStory() = runBlocking {
        val storyItem = createTestStoryItem()
        insertStoryUseCase(storyItem)

        assertEquals(storyItem, storyRepository.postedStory)

        storyRepository.deleteStory(storyItem)

        assertFalse(storyRepository.stories.contains(storyItem), "Story was not deleted successfully!")
    }
    @Test
    fun testDeleteAllStories() = runBlocking {
        val storyItem1 = createTestStoryItem()
        val storyItem2 = storyItem1.copy(id = 201)

        insertStoryUseCase(storyItem1)
        insertStoryUseCase(storyItem2)

        assertEquals(2, storyRepository.stories.size, "Expected 2 stories in the repository before deletion!")

        storyRepository.deleteAllStory()

        assertEquals(0, storyRepository.stories.size, "Expected all stories to be deleted!")
    }

    @Test
    fun testUpdateStory() = runBlocking {
        val originalStoryItem = createTestStoryItem()
        insertStoryUseCase(originalStoryItem)

        val updatedStoryItem = originalStoryItem.copy(imagePreview = "new_image_url")
        storyRepository.updateStory(updatedStoryItem)

        val updatedInRepository = storyRepository.stories.find { it.id == originalStoryItem.id }
        assertEquals(updatedStoryItem.imagePreview, updatedInRepository?.imagePreview, "Story was not updated successfully!")
    }

    @Test
    fun testGetStories() = runBlocking {
        val storyItem1 = createTestStoryItem()
        val storyItem2 = storyItem1.copy(id = 201)

        insertStoryUseCase(storyItem1)
        insertStoryUseCase(storyItem2)

        val storiesFlow = storyRepository.getStories()
        val stories = storiesFlow.toList().first()

        assertEquals(2, stories.size, "Incorrect number of stories retrieved!")
        assertTrue(stories.any { story -> story.id == storyItem1.id }, "First story not found in retrieved stories!")
        assertTrue(stories.any { story -> story.id == storyItem2.id }, "Second story not found in retrieved stories!")
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