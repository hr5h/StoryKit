package org.hrsh.story_kit.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import org.hrsh.story_kit.domain.entities.StoryItem
import org.hrsh.story_kit.presentation.story.StoryColors

@Composable
fun ShowFavoriteStories(
    favoriteStoriesList: List<StoryItem>,
    selectStory: (StoryItem) -> Unit,
    showStory: () -> Unit,
    saveShowFavoriteStories: () -> Unit,
    closeFavoriteStories: () -> Unit,
    colors: StoryColors
) {
    Dialog(onDismissRequest = { closeFavoriteStories() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize(0.5f),
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
                            .padding(start = 10.dp, top = 20.dp, end = 10.dp)
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