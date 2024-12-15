package org.hrsh.story_kit

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.hrsh.story_kit.di.Koin
import org.hrsh.story_kit.domain.entities.PageItem
import org.hrsh.story_kit.domain.entities.StoryItem
import org.hrsh.story_kit.presentation.MiniatureStories
import org.hrsh.story_kit.presentation.story.StoryViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        Koin.setupKoin()
        val storyViewModel = Koin.di?.koin?.get<StoryViewModel>()!!
        //PageItemImage
        storyViewModel.addStory(
            StoryItem(
                id = 100,
                imagePreview = "https://i01.fotocdn.net/s215/23442118aa73147b/public_pin_l/2920842511.jpg",
                listPages = listOf(
                    PageItem.Image(
                        imageUrl = "https://avatars.mds.yandex.net/i?id=325bcdf905e6685f354011427095fa3f_l-5233671-images-thumbs&n=13",
                        text = "Страница 1"
                    ),
                    PageItem.Image(
                        imageUrl = "https://avatars.mds.yandex.net/i?id=325bcdf905e6685f354011427095fa3f_l-5233671-images-thumbs&n=13",
                        text = "Страница 2"
                    ),
                    PageItem.Image(
                        imageUrl = "https://avatars.mds.yandex.net/i?id=325bcdf905e6685f354011427095fa3f_l-5233671-images-thumbs&n=13",
                        text = "Страница 3"
                    ),
                    PageItem.Question(
                        imageUrl = "https://avatars.yandex.net/get-music-content/5234847/767e884c.a.16290016-1/m1000x1000?webp=false",
                        question = "Как вы оцениваете наши истории?",
                        listAnswers = listOf("1", "2", "3", "4", "5+")
                    )
                ),
            )
        )
        storyViewModel.addStory(
            StoryItem(
                id = 200,
                imagePreview = "https://i01.fotocdn.net/s215/23442118aa73147b/public_pin_l/2920842511.jpg",
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
        )
        //PageItemVideo
        storyViewModel.addStory(
            StoryItem(
                id = 301,
                imagePreview = "https://vels76.ru/sites/default/files/znachok-videozapisi.jpg",
                listPages = listOf(
                    PageItem.Video(
                        videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
                    )
                ),
            )
        )
//        //PageItemError
//        storyViewModel.addStory(
//            StoryItem(
//                imagePreview = "https://rst-motors.ru/upload/iblock/e59/e59e1123414ef4f6203e1dad18c43618.jpg",
//                listPages = listOf(
//                    PageItem.Error
//                ),
//            )
//        )
        MiniatureStories(Color(red = 11, green = 172, blue = 65))
    }
}