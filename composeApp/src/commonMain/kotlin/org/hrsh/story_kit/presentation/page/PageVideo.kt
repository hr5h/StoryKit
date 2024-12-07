package org.hrsh.story_kit.presentation.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import org.hrsh.story_kit.di.VideoPlayer
import org.hrsh.story_kit.domain.entities.PageItem

@Composable
fun PageVideo(
    itemVideo: PageItem.Video, pageSize: Float
) {
    Box(modifier = Modifier.graphicsLayer {
        scaleX = pageSize
        scaleY = pageSize
    }) {
        VideoPlayer(modifier = Modifier.fillMaxSize(), itemVideo.videoUrl)
    }
}