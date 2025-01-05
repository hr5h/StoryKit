package org.hrsh.story_kit.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import org.hrsh.story_kit.di.Koin
import org.hrsh.story_kit.domain.entities.StoryItem
import org.hrsh.story_kit.presentation.story.Story
import org.hrsh.story_kit.presentation.story.StoryViewModel

const val COUNT_FAVORITE_STORY = 4

@Composable
fun MiniatureStories(
    content: Color = Color.Black,
    storyViewModel: StoryViewModel = Koin.di?.koin?.get<StoryViewModel>()!!
) {
    val storyState = storyViewModel.storyState.collectAsState().value
    val favoriteStoriesList = storyViewModel.favoriteStoriesList
    val stories = if(!storyState.showFavoriteStories) storyViewModel.storyFlowList.collectAsState().value else favoriteStoriesList
    LazyRow(
        modifier = Modifier
            .background(content)
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        items(stories) { story ->
            Card(
                modifier = Modifier
                    .padding(start = 5.dp, end = 5.dp)
                    .background(Color.Transparent)
                    .clip(RoundedCornerShape(20.dp))
                    .size(100.dp)
            ) {
                AsyncImage(
                    model = story.imagePreview,
                    contentDescription = "im4",
                    modifier = Modifier
                        .background(if (story.isViewed) Color.Transparent else Color.Black)
                        .padding(if (story.isViewed) 0.dp else 5.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .clickable {
                            storyViewModel.selectStory(story)
                            storyViewModel.showStory()
                        },
                    contentScale = ContentScale.Crop
                )
            }
        }
        if(favoriteStoriesList.isNotEmpty()) {
            item {
                Card(
                    modifier = Modifier
                        .padding(start = 5.dp, end = 5.dp)
                        .background(Color.Transparent)
                        .clip(RoundedCornerShape(20.dp))
                        .size(100.dp)
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .background(Color.LightGray)
                            .clickable {
                                storyViewModel.showFavoriteStories()
                            }
                    ) {
                        items(favoriteStoriesList.take(COUNT_FAVORITE_STORY)) { story ->
                            AsyncImage(
                                model = story.imagePreview,
                                contentDescription = "im4",
                                modifier = Modifier
                                    .background(Color.Transparent)
                                    .padding(5.dp)
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(10.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }
        }
    }
    if ((storyState.hasFirstStory || storyState.isShowStory) && storyState.currentStory != -1) {
        Story(
            stories = stories,
            selectStoryItem = storyViewModel.selectStoryItem,
            storyState = storyState,
            prevPage = storyViewModel::prevPage,
            nextPage = storyViewModel::nextPage,
            setStory = storyViewModel::setStory,
            onClose = {
                if (storyState.isShowStory)
                    storyViewModel.closeStory()
                else if (storyState.hasFirstStory)
                    storyViewModel.closeFirstStory()
                storyViewModel.unSelectStory()
                if(storyState.showFavoriteStories) {
                    storyViewModel.showFavoriteStories()
                    storyViewModel.saveCloseFavoriteStories()
                }
            },
            storyViewed = storyViewModel::storyViewed,
            storyLiked = storyViewModel::storyLiked,
            storyFavorited = storyViewModel::storyFavorited,
        )
    }
    if (storyState.isShowFavoriteStories) {
        if(favoriteStoriesList.isEmpty()) {
            storyViewModel.closeFavoriteStories()
            storyViewModel.saveCloseFavoriteStories()
        }
        else {
            ShowFavoriteStories(
                favoriteStoriesList,
                storyViewModel::selectStory,
                storyViewModel::showStory,
                storyViewModel::saveShowFavoriteStories,
                storyViewModel::closeFavoriteStories
            )
        }
    }
}

@Composable
fun ShowFavoriteStories(
    favoriteStoriesList: List<StoryItem>,
    selectStory: (StoryItem) -> Unit,
    showStory: () -> Unit,
    saveShowFavoriteStories: () -> Unit,
    closeFavoriteStories: () -> Unit,
) {
    Dialog(onDismissRequest = { closeFavoriteStories() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize(0.5f),
            shape = RoundedCornerShape(12.dp)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .background(Color.LightGray)
            ) {
                items(favoriteStoriesList) { story ->
                    AsyncImage(
                        model = story.imagePreview,
                        contentDescription = "im4",
                        modifier = Modifier
                            .background(Color.Transparent)
                            .padding(10.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .clickable {
                                selectStory(story)
                                saveShowFavoriteStories()
                                showStory()
                                closeFavoriteStories()
                            },
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}