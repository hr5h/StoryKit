package org.hrsh.story_kit.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.hrsh.story_kit.presentation.story.Story
import org.hrsh.story_kit.presentation.story.StoryViewModel

@Composable
fun MiniatureStories(url: String, content: Color = Color.Black, storyViewModel: StoryViewModel) {
    val stories = storyViewModel.storyList.collectAsState().value
    val isShow = storyViewModel.isShowStory.value
    val storyState = storyViewModel.storyState.collectAsState().value
    LazyRow(
        modifier = Modifier
            .background(content)
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        items(stories) { story ->
            Card(
                modifier = Modifier.padding(start = 5.dp, end = 5.dp).background(Color.Transparent)
            ) {
                AsyncImage(
                    model = story.imagePreview,
                    contentDescription = "im4",
                    modifier = Modifier
                        .background(content)
                        .clip(RoundedCornerShape(20.dp))
                        .size(100.dp)
                        .clickable {
                            storyViewModel.selectStory(story)
                            storyViewModel.showStory()
                        },
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
    if (isShow && storyState.currentStory != null) {
        Story(
            stories = stories,
            storyState = storyState,
            nextPage = storyViewModel::nextPage,
            prevPage = storyViewModel::prevPage,
            nextStory = storyViewModel::nextStory,
            prevStory = storyViewModel::prevStory,
            onClose = {
                storyViewModel.showStory()
                storyViewModel.unSelectStory()
            }
        )
    }
    //ShowFirstStory
    val firstStoryState = storyViewModel.firstStoryState.collectAsState().value
    if(firstStoryState.currentStory != null) {
        Story(
            stories = stories,
            storyState = firstStoryState,
            nextPage = storyViewModel::nextPageFirstStory,
            prevPage = storyViewModel::prevPageFirstStory,
            nextStory = {},
            prevStory = {},
            onClose = storyViewModel::firstStoryClose)
    }
}