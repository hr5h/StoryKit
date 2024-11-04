package org.hrsh.story_kit.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.hrsh.story_kit.presentation.story.Story
import org.hrsh.story_kit.presentation.story.StoryItem
import org.hrsh.story_kit.presentation.story.StoryViewModel

@Composable
fun MiniatureStories(url: String, content: Color = Color.Black) {
    val storyViewModel = StoryViewModel()
    val isShow = remember {
        mutableStateOf(false)
    }
    val stories = storyViewModel.storyList.collectAsState().value
    val selectStoryItem = remember {
        mutableStateOf(StoryItem("", emptyList()))
    }
    LazyRow(
        modifier = Modifier
            .background(content)
            .padding(top = 10.dp)
    ) {
        items(stories) { story ->
            Card(
                modifier = Modifier.padding(start = 5.dp, end = 5.dp)
            ) {
                AsyncImage(
                    model = story.imagePreview,
                    contentDescription = "im4",
                    modifier = Modifier
                        .size(80.dp)
                        .background(Color.White)
                        .clickable {
                            isShow.value = true
                            selectStoryItem.value = story
                        }
                )
            }
        }
    }

    if (isShow.value) {
        Story(selectStoryItem = selectStoryItem.value) {
            isShow.value = false
        }
    }
}