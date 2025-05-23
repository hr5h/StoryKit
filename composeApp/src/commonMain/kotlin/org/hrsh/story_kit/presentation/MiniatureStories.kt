package org.hrsh.story_kit.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import coil3.compose.AsyncImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.hrsh.story_kit.di.Koin
import org.hrsh.story_kit.di.Navigator
import org.hrsh.story_kit.presentation.story.Story
import org.hrsh.story_kit.presentation.story.StoryViewModel

internal const val COUNT_FAVORITE_STORY = 4

@Composable
internal fun MiniatureStories(
    storyViewModel: StoryViewModel = Koin.di?.koin?.get<StoryViewModel>()!!
) {
    val storyState = storyViewModel.storyState.collectAsState().value
    val favoriteStoriesList = storyViewModel.favoriteStoriesList.collectAsState().value
    val storiesList = storyViewModel.storyFlowList.collectAsState().value
    val navigator = Koin.di?.koin?.get<Navigator>()!!
    val colors = storyState.storyColors
    LazyRow(
        modifier = Modifier
            .background(colors.miniature)
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        items(storiesList) { story ->
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
                        .background(if (story.isViewed) Color.Transparent else colors.storyStroke)
                        .padding(if (story.isViewed) 0.dp else 3.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .clickable {
                            storyViewModel.selectStory(story)
                            storyViewModel.showStory()
                        },
                    contentScale = ContentScale.Crop
                )
            }
        }
        if (favoriteStoriesList.isNotEmpty()) {
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
                            .background(colors.favoritesPreview)
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
        navigator.navigateToStory()
    }
    if (storyState.isShowFavoriteStories) {
        //условие для того чтобы диалог пропадал, после того как последняя избранная история была удалена
        if (favoriteStoriesList.isEmpty()) {
            storyViewModel.closeFavoriteStories()
            storyViewModel.saveCloseFavoriteStories()
        } else {
            ShowFavoriteStories(
                favoriteStoriesList,
                storyViewModel::selectStory,
                storyViewModel::showStory,
                storyViewModel::saveShowFavoriteStories,
                storyViewModel::closeFavoriteStories,
                storyViewModel::updateFavoriteStories,
                colors
            )
        }
    }
}

@Composable
internal fun ShowStory(
    onClose: () -> Unit,
    storyViewModel: StoryViewModel = Koin.di?.koin?.get<StoryViewModel>()!!
) {
    val storyState = storyViewModel.storyState.collectAsState().value
    val favoriteStoriesList = storyViewModel.favoriteStoriesList.collectAsState().value
    val storiesList = storyViewModel.storyFlowList.collectAsState().value
    val stories = if (!storyState.showFavoriteStories) storiesList else favoriteStoriesList
    val colors = storyState.storyColors
    if ((storyState.hasFirstStory || storyState.isShowStory) && storyState.currentStory != -1) {
        Story(
            stories = stories,
            selectStoryItem = storyViewModel.selectStoryItem,
            storyState = storyState,
            prevPage = storyViewModel::prevPage,
            nextPage = storyViewModel::nextPage,
            setStory = storyViewModel::setStory,
            onClose = {
                CoroutineScope(Dispatchers.Main).launch {
                    onClose()
                    delay(200)
                    if (!storyState.showFavoriteStories) storyViewModel.updateStories() else storyViewModel.updateFavoriteStories()
                    storyViewModel.closeAllStory()
                }
            },
            storyViewed = storyViewModel::storyViewed,
            storyLiked = storyViewModel::storyLiked,
            storyFavorited = storyViewModel::storyFavorited,
            onChose = storyViewModel::updateSelected,
            pauseStory = storyViewModel::pauseStory,
            storySkip = storyViewModel::storySkip,
            colors = colors,
        )
    }
}