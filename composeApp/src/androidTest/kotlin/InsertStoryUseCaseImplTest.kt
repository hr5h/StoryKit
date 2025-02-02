import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import org.hrsh.story_kit.domain.entities.PageItem
import org.hrsh.story_kit.domain.entities.StoryItem
import org.hrsh.story_kit.domain.interfaces.StoryRepository
import org.hrsh.story_kit.domain.usecases.InsertStoryUseCaseImpl
import org.junit.Before
import org.junit.Test

class TestStoryRepository : StoryRepository {
    var postedStory: StoryItem? = null
    override suspend fun getStories(): Flow<List<StoryItem>> {
        TODO("Not yet implemented")
    }

    override suspend fun postStory(storyItem: StoryItem) {
        postedStory = storyItem
    }

    override suspend fun updateStory(storyItem: StoryItem) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteStory(storyItem: StoryItem) {
        TODO("Not yet implemented")
    }
}

class InsertStoryUseCaseImplTest {

    private lateinit var storyRepository: TestStoryRepository
    private lateinit var insertStoryUseCase: InsertStoryUseCaseImpl

    @Before
    fun setUp() {
        storyRepository = TestStoryRepository()
        insertStoryUseCase = InsertStoryUseCaseImpl(storyRepository)
    }

    @Test
    fun Test1() = runBlocking {
        // Arrange
        val storyItem = StoryItem(
            id = 200,
            imagePreview = "https://avatars.mds.yandex.net/i?id=e339fc622756af285f34aa7777d37444_l-5234706-images-thumbs&n=13",
            listPages = listOf(
                PageItem.Image(
                    imageUrl = "https://avatars.mds.yandex.net/i?id=325bcdf905e6685f354011427095fa3f_l-5233671-images-thumbs&n=13",
                    text = "Страница 1"
                ),
                PageItem.Image(
                    imageUrl = "https://avatars.mds.yandex.net/i?id=325bcdf905e6685f354011427095fa3f_l-5233671-images-thumbs&n=13",
                    text = "Страница 2"
                )
            )
        )

        insertStoryUseCase(storyItem)
        assert(storyRepository.postedStory == storyItem) { "Story was not posted correctly!" }
    }
}