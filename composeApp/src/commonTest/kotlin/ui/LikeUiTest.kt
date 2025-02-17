
@file:OptIn(ExperimentalTestApi::class)
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.hrsh.story_kit.domain.entities.StoryItem
import kotlin.test.Test
import androidx.compose.ui.test.runComposeUiTest
import org.hrsh.story_kit.domain.entities.PageItem
import org.hrsh.story_kit.presentation.story.LikeAndFavorite
import org.hrsh.story_kit.presentation.story.StoryColors
import kotlin.test.assertEquals

class LikeTest {
    @OptIn(ExperimentalTestApi::class)
    @Test
    fun likeCounter() = runComposeUiTest() {
        val storyItem = StoryItem(
            id = 444400,
            imagePreview = "https://avatars.mds.yandex.net/i?id=e339fc622756af285f34aa7777d37444_l-5234706-images-thumbs&n=13",
            listPages = listOf(
                PageItem.Image(
                    imageUrl = "https://avatars.mds.yandex.net/i?id=325bcdf905e6685f354011427095fa3f_l-5233671-images-thumbs&n=13",
                    text = "Страница 1"
                ),
                PageItem.Image(
                    imageUrl = "https://avatars.mds.yandex.net/i?id=325bcdf905e6685f354011427095fa3f_l-5233671-images-thumbs&n=13",
                    text = "Страница 2"
                ),
            ),
        )
        val storyColors = StoryColors(
            miniature = Color(red = 11, green = 172, blue = 65),
            storyStroke = Color.Green,
            favoritesPreview = Color(144, 238, 144),
            favoritesDialog = Color(144, 238, 144),
        )
        setContent {
            val state = remember { mutableStateOf(storyItem) }
            Column {
                LikeAndFavorite(
                    selectStoryItem = state.value,
                    storyLiked = {
                        state.value = state.value.copy(
                            isLike = !state.value.isLike,
                            countLike = if (state.value.isLike) state.value.countLike - 1 else state.value.countLike + 1
                        )
                    },
                    storyFavorited = {
                        state.value = state.value.copy(
                            isFavorite = !state.value.isFavorite,
                        )
                    },
                    colors = storyColors
                )
            }
        }

        onNodeWithText("0").assertExists()
        onNodeWithText("1").assertDoesNotExist()
        onNodeWithContentDescription("like").performClick()
        onNodeWithText("1").assertExists()
        onNodeWithText("0").assertDoesNotExist()
        onNodeWithContentDescription("like").performClick()
        onNodeWithText("0").assertExists()
        onNodeWithText("1").assertDoesNotExist()


    }


}