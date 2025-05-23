package org.hrsh.story_kit.presentation.story

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import org.hrsh.story_kit.di.BackHandler
import org.hrsh.story_kit.di.getScreenHeightDp
import org.hrsh.story_kit.di.getScreenWidthDp
import org.hrsh.story_kit.domain.entities.PageItem
import org.hrsh.story_kit.domain.entities.StoryItem
import org.hrsh.story_kit.presentation.page.PageError
import org.hrsh.story_kit.presentation.page.PageImage
import org.hrsh.story_kit.presentation.page.PageQuestion
import org.hrsh.story_kit.presentation.page.PageVideo
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.roundToInt

@Composable
internal fun Story(
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
    onChose: (StoryItem, PageItem, Int) -> Unit,
    pauseStory: (Boolean) -> Unit,
    storySkip: (Long, Int, Float) -> Unit,
    colors: StoryColors,
) {
    val pages =
        if (storyState.hasFirstStory) listOf(selectStoryItem.listPages[storyState.currentPage[storyState.currentStory]])
        else stories.mapIndexed { index, storyItem ->
            storyItem.listPages[storyState.currentPage[index]]
        }

    val isAnimateTimeLine = remember { mutableStateOf(true) }

    val backHandler = remember {
        BackHandler(enabled = true) {
            onClose()
        }
    }
    backHandler.setup()
    DisposableEffect(backHandler) {
        onDispose { backHandler.dispose() }
    }

    DraggableColumn(
        modifier = Modifier,
        height = getScreenHeightDp(),
        width = getScreenWidthDp(),
        ratio = 0.7f,
        dampingRatio = Spring.DampingRatioLowBouncy,
        stiffness = Spring.StiffnessLow,
        onActionTriggered = {
            onClose()
        },
        content = {
            val currentPage =
                stories[storyState.currentStory].listPages[storyState.currentPage[storyState.currentStory]]

            val indCurrentPage = storyState.currentPage[storyState.currentStory]
            val pageIdState = remember {
                mutableStateOf(0)
            }
            if (indCurrentPage != pageIdState.value) {
                pageIdState.value = indCurrentPage
            }

            val storyId = selectStoryItem.id
            val storyIdState = remember {
                mutableStateOf(0L)
            }
            if (storyId != storyIdState.value) {
                storyIdState.value = storyId
            }
            val currentTime =
                remember(storyState.currentStory) {
                    mutableStateListOf(*selectStoryItem.listPages.map { 0f }.toTypedArray())
                }

            TopBar(
                storyState,
                selectStoryItem,
                nextPage,
                isAnimateTimeLine,
                colors,
                currentPage,
                indCurrentPage,
                currentTime
            )
            Content(
                prevPage,
                nextPage,
                storyState,
                pages,
                indCurrentPage,
                setStory,
                onClose,
                selectStoryItem,
                storyViewed,
                onChose,
                colors,
                isAnimateTimeLine,
                storySkip,
                storyIdState,
                pageIdState,
                currentTime
            )
            LikeAndFavorite(selectStoryItem, storyLiked, storyFavorited, colors)
        },
        backgroundColor = Color.Transparent
    )

    LaunchedEffect(isAnimateTimeLine.value) {
        if (isAnimateTimeLine.value) pauseStory(false) else pauseStory(true)
    }
}

@Composable
private fun ColumnScope.TopBar(
    storyState: StoryState,
    selectStoryItem: StoryItem,
    nextPage: () -> Unit,
    isAnimateTimeLine: MutableState<Boolean>,
    colors: StoryColors,
    currentPage: PageItem,
    indCurrentPage: Int,
    currentTime: SnapshotStateList<Float>,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .background(colors.storyTopBar)
            .padding(5.dp),
    ) {
        LaunchedEffect(storyState.currentStory) {
            currentTime.indices.forEach { i ->
                currentTime[i] = 0f
            }
        }

        LaunchedEffect(indCurrentPage, storyState.currentStory) {
            currentTime.indices.forEach { i ->
                if (i != indCurrentPage) currentTime[i] = 0f
            }

            while (currentTime[indCurrentPage] < selectStoryItem.listPages[indCurrentPage].timeShow) {
                delay(20)
                if (isAnimateTimeLine.value) {
                    currentTime[indCurrentPage] += 0.02f
                }
            }

            nextPage()
        }

        TimeLine(
            selectStoryItem.listPages.size,
            indCurrentPage,
            currentTime[indCurrentPage],
            currentPage.timeShow,
            colors
        )

        DisposableEffect(lifecycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_START) {
                    isAnimateTimeLine.value = true
                } else if (event == Lifecycle.Event.ON_STOP) {
                    isAnimateTimeLine.value = false
                }
            }

            lifecycleOwner.lifecycle.addObserver(observer)

            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    }
}

@Composable
private fun TimeLine(
    countTimeLine: Int,
    currentTimeLine: Int,
    currentTime: Float,
    maxTime: Float,
    colors: StoryColors
) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .background(colors.storyTopBar)
            .padding(10.dp)
    ) {
        val widthTimeLine = (size / countTimeLine.toFloat()).width
        val heightTimeLine = 10.dp.toPx()
        val offsetTimeLine = 2f
        val cornerRadius = 10.dp.toPx()
        (0..<countTimeLine).forEach {
            drawRoundRect(
                color = if (it < currentTimeLine) colors.timeLine else colors.timeLineBackground,
                size = size.copy(
                    height = heightTimeLine,
                    width = widthTimeLine - offsetTimeLine * (countTimeLine - 1)
                ),
                topLeft = Offset(x = it * (widthTimeLine + offsetTimeLine), y = 0f),
                cornerRadius = CornerRadius(x = cornerRadius, y = cornerRadius)
            )
        }
        val leftTime = maxTime - currentTime
        val widthCurrentTime = widthTimeLine / maxTime * leftTime
        drawRoundRect(
            color = colors.timeLine,
            size = size.copy(
                height = heightTimeLine,
                width = max(
                    (widthTimeLine - widthCurrentTime) - offsetTimeLine * (countTimeLine - 1),
                    0f
                )
            ),
            topLeft = Offset(x = currentTimeLine * (widthTimeLine + offsetTimeLine), y = 0f),
            cornerRadius = CornerRadius(x = cornerRadius, y = cornerRadius)
        )
    }
}

@Composable
private fun ColumnScope.Content(
    prevPage: () -> Unit,
    nextPage: () -> Unit,
    storyState: StoryState,
    pages: List<PageItem>,
    indCurrentPage: Int,
    setStory: (Int) -> Unit,
    onClose: () -> Unit,
    selectStoryItem: StoryItem,
    storyViewed: (StoryItem) -> Unit,
    onChose: (StoryItem, PageItem, Int) -> Unit,
    colors: StoryColors,
    isAnimate: MutableState<Boolean>,
    storySkip: (Long, Int, Float) -> Unit,
    storyIdState: MutableState<Long>,
    pageIdState: MutableState<Int>,
    currentTime: SnapshotStateList<Float>,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .weight(17f)
            .background(colors.storyBackground)
            .pointerInput(currentTime) {
                detectTapGestures(
                    onLongPress = {
                        isAnimate.value = false
                    },
                    onPress = {
                        if (tryAwaitRelease()) {
                            isAnimate.value = true
                        }
                    },
                    onTap = { offset ->
                        if (offset.x < size.width / 2) {
                            storySkip(storyIdState.value, pageIdState.value, currentTime[pageIdState.value])
                            prevPage()
                        } else {
                            storySkip(storyIdState.value, pageIdState.value, currentTime[pageIdState.value])
                            nextPage()
                        }
                    }
                )
            }
    ) {
        var lastPage by remember { mutableStateOf(-1) }
        var lastStory by remember { mutableStateOf(-1) }
        val pagerState = rememberPagerState(
            initialPage = storyState.currentStory,
            pageCount = { pages.size })
        HorizontalPager(
            state = pagerState,
            modifier = Modifier,
            beyondViewportPageCount = 10
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
                snapshotFlow { storyState.currentStory }
                    .collectLatest { newStory ->
                        if (newStory != lastStory) {
                            lastStory = newStory
                            storyViewed(selectStoryItem)
                            pagerState.animateScrollToPage(storyState.currentStory)
                        }
                    }
            }
            val widthScreen = getScreenWidthDp()
            when (pages[index]) {
                is PageItem.Image -> PageImage(
                    pages[index] as PageItem.Image,
                    pageSize,
                    selectStoryItem.listPages.size,
                    indCurrentPage,
                    colors.lowerBlackout
                )

                is PageItem.Video -> PageVideo(
                    pages[index] as PageItem.Video,
                    pageSize,
                    pages[pagerState.currentPage] is PageItem.Video,
                    isAnimate,
                    { offset ->
                        if (offset.x.dp < widthScreen / 2) {
                            storySkip(storyIdState.value, pageIdState.value, currentTime[pageIdState.value])
                            prevPage()
                        } else {
                            storySkip(storyIdState.value, pageIdState.value, currentTime[pageIdState.value])
                            nextPage()
                        }
                    }
                )

                is PageItem.Question -> PageQuestion(
                    pages[index] as PageItem.Question,
                    pageSize,
                    selectStoryItem,
                    onChose,
                    colors
                )

                is PageItem.Game -> TODO()
                is PageItem.Error -> PageError(pageSize)
            }
        }
        //Cross
        Cross(onClose, colors.upperBlackout)
    }
}

@Composable
private fun BoxScope.Cross(onClose: () -> Unit, upperBlackout: Boolean) {
    val blackoutGradient = Brush.verticalGradient(
        0.05f to Color(0f, 0f, 0f, 0.5f),
        1.0f to Color.Transparent,
        startY = 0.0f,
        endY = 600.0f
    )
    val transparentGradient = Brush.verticalGradient(
        colors = listOf(
            Color.Transparent,
            Color.Transparent
        )
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(5f)
            .background(if (upperBlackout) blackoutGradient else transparentGradient)
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
fun ColumnScope.LikeAndFavorite(
    selectStoryItem: StoryItem,
    storyLiked: (StoryItem) -> Unit,
    storyFavorited: (StoryItem) -> Unit,
    colors: StoryColors
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .weight(2f)
            .background(colors.storyBottomBar)
            .padding(5.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        val isPressedLike = remember { mutableStateOf(false) }
        val sizeLike = AnimatedSize(
            isPressed = isPressedLike,
            sizeMin = 40.dp,
            sizeMax = 48.dp,
            timeAnimation = 200
        )
        IconButton(
            modifier = Modifier
                .padding(10.dp),
            onClick = {
                storyLiked(selectStoryItem)
                isPressedLike.value = true
            }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(sizeLike.value),
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "like",
                    tint = if (selectStoryItem.isLike) colors.isLiked else colors.isNotLiked
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
        val isPressedFavorite = remember { mutableStateOf(false) }
        val sizeFavorite = AnimatedSize(
            isPressed = isPressedFavorite,
            sizeMin = 40.dp,
            sizeMax = 48.dp,
            timeAnimation = 200
        )
        IconButton(
            modifier = Modifier
                .padding(10.dp),
            onClick = {
                storyFavorited(selectStoryItem)
                isPressedFavorite.value = true
            }
        ) {
            Icon(
                modifier = Modifier.size(sizeFavorite.value),
                imageVector = Icons.Default.Star,
                contentDescription = "star",
                tint = if (selectStoryItem.isFavorite) colors.isFavorite else colors.isNotFavorite
            )
        }
    }
}

@Composable
private fun AnimatedSize(
    isPressed: MutableState<Boolean>,
    sizeMin: Dp,
    sizeMax: Dp,
    timeAnimation: Int,
): State<Dp> {
    val size by animateDpAsState(
        targetValue = if (isPressed.value) sizeMax else sizeMin,
        animationSpec = tween(timeAnimation),
    )

    LaunchedEffect(isPressed.value) {
        if (isPressed.value) {
            delay(timeAnimation.toLong())
            isPressed.value = false
        }
    }

    return derivedStateOf { size }
}

@Composable
fun DraggableColumn(
    modifier: Modifier = Modifier,
    height: Dp = 300.dp,
    width: Dp = 300.dp,
    ratio: Float = 0.5f,
    dampingRatio: Float = Spring.DampingRatioMediumBouncy,
    stiffness: Float = Spring.StiffnessLow,
    onActionTriggered: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit = {},
    backgroundColor: Color = Color.LightGray
) {
    var offsetY by remember { mutableFloatStateOf(0f) }
    var isDragging by remember { mutableStateOf(false) }
    val wasThresholdCrossed = remember { mutableStateOf(false) }

    val density = LocalDensity.current
    val triggerThreshold = remember {
        with(density) { height.toPx() * ratio }
    }

    val animatedOffset by animateFloatAsState(
        targetValue = if (isDragging) offsetY else 0f,
        animationSpec = spring(
            dampingRatio = dampingRatio,
            stiffness = stiffness
        ),
        label = "offsetAnimation"
    )

    Box(
        modifier = modifier.then(
            Modifier
                .height(height)
                .fillMaxWidth()
                .pointerInput(Unit) {
                    detectVerticalDragGestures(
                        onDragStart = {
                            isDragging = true
                            wasThresholdCrossed.value = false
                        },
                        onDragEnd = {
                            isDragging = false
                            if (wasThresholdCrossed.value) {
                                onActionTriggered()
                            }
                            offsetY = 0f
                        },
                        onVerticalDrag = { _, dragAmount ->
                            offsetY += dragAmount
                            offsetY = offsetY.coerceAtMost(0f)

                            val crossedThreshold = abs(offsetY) >= triggerThreshold
                            if (crossedThreshold && !wasThresholdCrossed.value) {
                                wasThresholdCrossed.value = true
                            } else if (!crossedThreshold) {
                                wasThresholdCrossed.value = false
                            }
                        }
                    )
                }
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .offset { IntOffset(0, animatedOffset.roundToInt()) }
                .background(backgroundColor)
        ) {
            content()
        }
    }
}