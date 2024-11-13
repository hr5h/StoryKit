package org.hrsh.story_kit

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.hrsh.story_kit.presentation.MiniatureStories
import org.hrsh.story_kit.presentation.page.PageItem
import org.hrsh.story_kit.presentation.story.Story
import org.hrsh.story_kit.presentation.story.StoryItem
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
                        text = "Кошки — одни из самых популярных домашних животных по всему миру. " +
                                "Их древняя история и уникальные качества привлекают внимание людей на " +
                                "протяжении многих веков."
                    )
                )
            )
        )
        //PageItemError
        storyViewModel.addStory(
            StoryItem(
                imagePreview = "https://rst-motors.ru/upload/iblock/e59/e59e1123414ef4f6203e1dad18c43618.jpg",
                listPages = listOf(
                    PageItem.PageItemError
                )
            )
        )
        MiniatureStories("", Color(red = 11, green = 172, blue = 65), storyViewModel)
    }
}

@Composable
fun ShowPageImage() {
    Story(
        StoryItem(
            imagePreview = "https://avatar.rf4game.com/rf4game.ru/wp-content/uploads/avatar/256/2731/2731746.jpg",
            listPages = listOf(
                PageItem.PageItemImage(
                    image = "https://avatars.mds.yandex.net/i?id=325bcdf905e6685f354011427095fa3f_l-5233671-images-thumbs&n=13",
                    text = "Кошки — одни из самых популярных домашних животных по всему миру. " +
                            "Их древняя история и уникальные качества привлекают внимание людей на " +
                            "протяжении многих веков."
                )
            )
        )
    ) {}
}

@Composable
fun ShowPageVideo() {
}

@Composable
fun ShowPageQuestion() {
}

@Composable
fun ShowPageError() {
    Story(
        StoryItem(
            imagePreview = "https://avatar.rf4game.com/rf4game.ru/wp-content/uploads/avatar/256/2731/2731746.jpg",
            listPages = listOf(
                PageItem.PageItemError
            )
        )
    ) {}
}
