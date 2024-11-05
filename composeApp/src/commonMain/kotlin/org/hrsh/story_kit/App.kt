package org.hrsh.story_kit

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import org.hrsh.story_kit.presentation.page.PageItem
import org.hrsh.story_kit.presentation.story.Story
import org.hrsh.story_kit.presentation.story.StoryItem
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        //ShowPageImage()
        //ShowPageError()
    }
}

@Composable
fun ShowPageImage(){
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
    ){}
}

@Composable
fun ShowPageVideo(){
}

@Composable
fun ShowPageQuestion(){
}

@Composable
fun ShowPageError(){
    Story(
        StoryItem(
            imagePreview = "https://avatar.rf4game.com/rf4game.ru/wp-content/uploads/avatar/256/2731/2731746.jpg",
            listPages = listOf(
                PageItem.PageItemError
            )
        )
    ){}
}
