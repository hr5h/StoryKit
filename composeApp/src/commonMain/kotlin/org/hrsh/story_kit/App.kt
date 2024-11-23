package org.hrsh.story_kit

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.hrsh.story_kit.presentation.MiniatureStories
import org.hrsh.story_kit.domain.PageItem
import org.hrsh.story_kit.domain.StoryItem
import org.hrsh.story_kit.presentation.story.StoryViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        val storyViewModel = StoryViewModel()
        //PageItemImage
        storyViewModel.addStory(
            StoryItem(
                imagePreview = "https://i01.fotocdn.net/s215/23442118aa73147b/public_pin_l/2920842511.jpg",
                listPages = listOf(
                    PageItem.PageItemImage(
                        image = "https://avatars.mds.yandex.net/i?id=325bcdf905e6685f354011427095fa3f_l-5233671-images-thumbs&n=13",
                        text = "Страница 1"
                    ),
                    PageItem.PageItemImage(
                        image = "https://avatars.mds.yandex.net/i?id=325bcdf905e6685f354011427095fa3f_l-5233671-images-thumbs&n=13",
                        text = "Страница 2"
                    ),
                    PageItem.PageItemImage(
                        image = "https://avatars.mds.yandex.net/i?id=325bcdf905e6685f354011427095fa3f_l-5233671-images-thumbs&n=13",
                        text = "Страница 3"
                    )
                ),
                showOnStart = false
            )
        )
        //PageItemVideo
        storyViewModel.addStory(
            StoryItem(
                imagePreview = "https://vels76.ru/sites/default/files/znachok-videozapisi.jpg",
                listPages = listOf(
                    PageItem.PageItemVideo(
                        video = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
                    )
                ),
                showOnStart = false
            )
        )
        MiniatureStories("", Color(red = 11, green = 172, blue = 65), storyViewModel)
    }
}