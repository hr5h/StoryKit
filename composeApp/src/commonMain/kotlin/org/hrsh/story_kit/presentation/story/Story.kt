package org.hrsh.story_kit.presentation.story

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.collectLatest
import org.hrsh.story_kit.domain.entities.PageItem
import org.hrsh.story_kit.domain.entities.StoryItem
import org.hrsh.story_kit.presentation.page.PageError
import org.hrsh.story_kit.presentation.page.PageImage
import org.hrsh.story_kit.presentation.page.PageQuestion
import org.hrsh.story_kit.presentation.page.PageVideo

@Composable
fun Story(
    stories: List<StoryItem>,
    selectStoryItem: StoryItem,
    storyState: StoryState,
    prevPage: () -> Unit,
    nextPage: () -> Unit,
    setStory: (Int) -> Unit,
    onClose: () -> Unit,
    storyViewed: (StoryItem) -> Unit,
    storyLiked: (StoryItem) -> Unit,
    storyFavorited: (StoryItem) -> Unit,
) {
    val pages =
        if (storyState.hasFirstStory) listOf(selectStoryItem.listPages[storyState.currentPage[storyState.currentStory]])
        else stories.mapIndexed { index, storyItem ->
            storyItem.listPages[storyState.currentPage[index]]
        }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        //TimeLine
        TimeLine(storyState, stories, selectStoryItem)
        //Content
        Content(prevPage, nextPage, storyState, pages, setStory, onClose, selectStoryItem, storyViewed)
        //LikeAndFavorite
        LikeAndFavorite(selectStoryItem, storyLiked, storyFavorited)
    }
}

@Composable
private fun ColumnScope.TimeLine(
    storyState: StoryState,
    stories: List<StoryItem>,
    selectStoryItem: StoryItem
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .background(Color.Black)
            .padding(5.dp),
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "Текущая история: ${storyState.currentStory + 1}/${stories.size}\n"
                    + "Текущая страница: ${storyState.currentPage[storyState.currentStory] + 1}/${selectStoryItem.listPages.size}",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 13.sp,
            lineHeight = 14.sp
        )
    }
}

@Composable
private fun ColumnScope.Content(
    prevPage: () -> Unit,
    nextPage: () -> Unit,
    storyState: StoryState,
    pages: List<PageItem>,
    setStory: (Int) -> Unit,
    onClose: () -> Unit,
    selectStoryItem: StoryItem,
    storyViewed: (StoryItem) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .weight(17f)
            .background(Color.Black)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { offset ->
                        if (offset.x < size.width / 2) {
                            prevPage()
                        } else {
                            nextPage()
                        }
                    }
                )
            }
    ) {
        var lastPage by remember { mutableStateOf(-1) }
        val pagerState = rememberPagerState(
            initialPage = storyState.currentStory,
            pageCount = { pages.size })
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
        ) { index ->
            val pageOffset =
                (pagerState.currentPage - index) + pagerState.currentPageOffsetFraction
            val pageSize = animateFloatAsState(
                targetValue = if (pageOffset != 0.0f) 0.95f else 1f,
                animationSpec = tween(
                    durationMillis = 50
                ), label = ""
            ).value
            LaunchedEffect(pagerState) {
                snapshotFlow { pagerState.currentPage }
                    .collectLatest { newPage ->
                        if (newPage != lastPage) {
                            lastPage = newPage
                            if (!storyState.hasFirstStory) setStory(lastPage)
                        }
                    }
            }
            LaunchedEffect(storyState.currentStory) {
                storyViewed(selectStoryItem)
                pagerState.animateScrollToPage(storyState.currentStory)
            }
            when (pages[index]) {
                is PageItem.Image -> PageImage(
                    pages[index] as PageItem.Image,
                    pageSize
                )

                is PageItem.Video -> PageVideo(
                    pages[index] as PageItem.Video,
                    pageSize
                )

                is PageItem.Question -> PageQuestion(
                    pages[index] as PageItem.Question,
                    pageSize
                )

                is PageItem.Game -> TODO()
                is PageItem.Error -> PageError(pageSize)
            }
        }
        //Cross
        Cross(onClose)
    }
}

@Composable
private fun BoxScope.Cross(onClose: () -> Unit) {
    val gradient = Brush.verticalGradient(
        0.05f to Color(0f, 0f, 0f, 0.5f),
        1.0f to Color.Transparent,
        startY = 0.0f,
        endY = 600.0f
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(5f)
            .background(gradient)
            .align(Alignment.TopEnd),
    ) {
        IconButton(
            modifier = Modifier
                .padding(top = 10.dp)
                .align(Alignment.TopEnd),
            onClick = {
                onClose()
            }) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "close",
                tint = Color.White
            )
        }
    }
}

@Composable
private fun ColumnScope.LikeAndFavorite(
    selectStoryItem: StoryItem,
    storyLiked: (StoryItem) -> Unit,
    storyFavorited: (StoryItem) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .weight(2f)
            .background(Color.Black)
            .padding(5.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        IconButton(modifier = Modifier
            .padding(10.dp),
            onClick = {
                storyLiked(selectStoryItem)
            }) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(40.dp),
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "like",
                    tint = if(selectStoryItem.isLike) Color.Red else Color.White
                )
                Text(
                    modifier = Modifier
                        .padding(start = 5.dp, top = 5.dp, end = 10.dp),
                    text = "${selectStoryItem.countLike}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
        IconButton(modifier = Modifier
            .padding(10.dp), onClick = {
                storyFavorited(selectStoryItem)
        }) {
            Icon(
                modifier = Modifier.size(40.dp),
                imageVector = Icons.Default.Star,
                contentDescription = "star",
                tint = if(selectStoryItem.isFavorite) Color.Yellow else Color.White
            )
        }
    }
}