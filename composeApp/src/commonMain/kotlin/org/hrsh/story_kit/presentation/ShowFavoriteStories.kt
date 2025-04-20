package org.hrsh.story_kit.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil3.compose.AsyncImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.hrsh.story_kit.domain.entities.StoryItem
import org.hrsh.story_kit.presentation.story.StoryColors

@Composable
internal fun ShowFavoriteStories(
    favoriteStoriesList: List<StoryItem>,
    selectStory: (StoryItem) -> Unit,
    showStory: () -> Unit,
    saveShowFavoriteStories: () -> Unit,
    closeFavoriteStories: () -> Unit,
    updateFavoriteStories: () -> Unit,
    colors: StoryColors
) {
    LaunchedEffect(Unit) {
        delay(300)
        updateFavoriteStories()
    }

    var showAnimatedDialog by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        showAnimatedDialog = true
    }

    Dialog(
        onDismissRequest = {
            CoroutineScope(Dispatchers.Main).launch {
                showAnimatedDialog = false
                delay(300)
                closeFavoriteStories()
            }
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .fillMaxHeight(0.5f),
            contentAlignment = Alignment.Center
        ) {
            AnimatedVisibility(
                visible = showAnimatedDialog,
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                Box(
                    modifier = Modifier
                        .pointerInput(Unit) { detectTapGestures { closeFavoriteStories() } }
                        .fillMaxSize()
                )
            }
            AnimatedVisibility(
                visible = showAnimatedDialog,
                enter = fadeIn(spring(stiffness = Spring.StiffnessHigh)) + scaleIn(
                    initialScale = .8f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMediumLow
                    )
                ),
                exit = slideOutVertically { it / 8 } + fadeOut() + scaleOut(targetScale = .95f)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxSize(),
                    shape = RoundedCornerShape(12.dp),
                    elevation = 8.dp
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .background(colors.favoritesDialog)
                    ) {
                        items(favoriteStoriesList) { story ->
                            AsyncImage(
                                model = story.imagePreview,
                                contentDescription = "im4",
                                modifier = Modifier
                                    .background(Color.Transparent)
                                    .padding(
                                        start = 10.dp,
                                        top = 10.dp,
                                        end = 10.dp,
                                        bottom = 10.dp
                                    )
                                    .aspectRatio(1f)
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
    }
}