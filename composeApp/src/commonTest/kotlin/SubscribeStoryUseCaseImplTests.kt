import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import org.hrsh.story_kit.domain.entities.PageItem
import org.hrsh.story_kit.domain.entities.StoryItem
import org.hrsh.story_kit.domain.usecases.SubscribeStoryUseCaseImpl
import org.hrsh.story_kit.domain.repositories.TestStoryRepository

class SubscribeStoryUseCaseImplTests {

    private lateinit var storyRepository: TestStoryRepository
    private lateinit var subscribeStoryUseCase: SubscribeStoryUseCaseImpl

    @BeforeTest
    fun setUp() {
        storyRepository = TestStoryRepository()
        subscribeStoryUseCase = SubscribeStoryUseCaseImpl(storyRepository)
    }

    @Test
    fun testSubscribeToStories() = runBlocking {
        val storyItem1 = createTestStoryItem(id = 1L)
        val storyItem2 = createTestStoryItem(id = 2L)
        storyRepository.postStory(storyItem1)
        storyRepository.postStory(storyItem2)
        val storiesFlow = subscribeStoryUseCase.invoke()
        val stories = storiesFlow.toList()

        println("Fetched stories: $stories")
        assertEquals(1, stories.size)
        assertEquals(listOf(storyItem1, storyItem2), stories[0], "Список историй должен совпадать с опубликованными!")
    }

    @Test
    fun testSubscribeToEmptyStories() = runBlocking {
        val storiesFlow = subscribeStoryUseCase.invoke()
        val stories = storiesFlow.toList()

        println("Fetched stories: $stories")
        assertEquals(1, stories.size)
        assertEquals(emptyList<StoryItem>(), stories[0], "Список историй должен быть пустым!")
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