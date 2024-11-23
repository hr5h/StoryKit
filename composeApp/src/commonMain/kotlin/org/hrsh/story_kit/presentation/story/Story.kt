package org.hrsh.story_kit.presentation.story

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import kotlinx.coroutines.flow.collectLatest
import org.hrsh.story_kit.presentation.page.PageError
import org.hrsh.story_kit.presentation.page.PageImage
import org.hrsh.story_kit.domain.PageItem
import org.hrsh.story_kit.domain.StoryItem
import org.hrsh.story_kit.presentation.page.PageQuestion
import org.hrsh.story_kit.presentation.page.PageVideo
import kotlin.math.abs

@Composable
fun Story(
    stories: List<StoryItem>,
    storyState: StoryState,
    prevPage: () -> Unit,
    nextPage: () -> Unit,
    setStory: (Int) -> Unit,
    onClose: () -> Unit
) {
    val text = remember {
        mutableIntStateOf(0)
    }
    val selectStoryItem = storyState.currentStory.let { stories[it] }
    val pages = stories.mapIndexed { index, storyItem ->
        storyItem.listPages[storyState.currentPage[index]]
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        //TimeLine
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
        //Content
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
                                setStory(lastPage)
                            }
                        }
                }
                LaunchedEffect(storyState.currentStory) {
                    pagerState.animateScrollToPage(storyState.currentStory)
                }
                when (pages[index]) {
                    is PageItem.PageItemImage -> PageImage(
                        pages[index] as PageItem.PageItemImage,
                        pageSize
                    )

                    is PageItem.PageItemVideo -> PageVideo(
                        pages[index] as PageItem.PageItemVideo,
                        pageSize
                    )

                    is PageItem.PageItemQuestion -> PageQuestion(
                        pages[index] as PageItem.PageItemQuestion,
                        pageSize
                    )
                    is PageItem.PageItemGame -> TODO()
                    is PageItem.PageItemError -> PageError(pageSize)
                }
            }
            //Cross
            val gradient = Brush.verticalGradient(
                0.05f to Color(0f, 0f, 0f, 0.5f),
                1.0f to Color.Transparent,
                startY = 0.0f,
                endY = 600.0f
            )
            Box(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(5f).background(gradient)
                    .align(Alignment.TopEnd),
            ) {
                IconButton(
                    modifier = Modifier.padding(top = 10.dp).align(Alignment.TopEnd),
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
        //LikeAndFavorite
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
                .background(Color.Black)
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            IconButton(modifier = Modifier
                .padding(10.dp), onClick = { text.value += 1 }) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(40.dp),
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "like",
                        tint = Color.White
                    )
                    Text(
                        modifier = Modifier
                            .padding(start = 5.dp, top = 5.dp, end = 10.dp),
                        text = "${text.value}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
            IconButton(modifier = Modifier
                .padding(10.dp), onClick = {}) {
                Icon(
                    modifier = Modifier.size(40.dp),
                    imageVector = Icons.Default.Star,
                    contentDescription = "star",
                    tint = Color.White
                )
            }
        }
    }
}