package org.hrsh.story_kit.presentation.story

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
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.hrsh.story_kit.presentation.page.PageError
import org.hrsh.story_kit.presentation.page.PageImage
import org.hrsh.story_kit.presentation.page.PageItem
import org.hrsh.story_kit.presentation.page.PageQuestion
import org.hrsh.story_kit.presentation.page.PageVideo
import kotlin.math.abs

@Composable
fun Story(
    stories: List<StoryItem>,
    storyState: StoryState,
    nextPage: () -> Unit,
    prevPage: () -> Unit,
    nextStory: () -> Unit,
    prevStory: () -> Unit,
    onClose: () -> Unit
) {
    val text = remember {
        mutableIntStateOf(0)
    }
    val selectStoryItem = storyState.currentStory!!.let { stories[it] }
    val currentPage = selectStoryItem.listPages[storyState.currentPage[storyState.currentStory]]
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
                .background(Color.LightGray)
                .pointerInput(Unit) {
                    //Переход между историми по свайпу
                    detectHorizontalDragGestures { change, dragAmount ->
                        if (abs(dragAmount) > 40) {
                            if (dragAmount > 0) {
                                prevStory()
                            } else if (dragAmount < 0) {
                                nextStory()
                            }
                        }
                        change.consume()
                    }
                }
                .pointerInput(Unit) {
                    //Переход между страницами по тапу
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
            when (currentPage) {
                is PageItem.PageItemImage -> PageImage((currentPage as PageItem.PageItemImage))
                is PageItem.PageItemVideo -> PageVideo()
                is PageItem.PageItemQuestion -> PageQuestion()
                is PageItem.PageItemGame -> TODO()
                is PageItem.PageItemError -> PageError()
            }
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