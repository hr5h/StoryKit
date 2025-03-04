package org.hrsh.story_kit.presentation.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import org.hrsh.story_kit.di.Koin
import org.hrsh.story_kit.di.VideoPlayer
import org.hrsh.story_kit.domain.entities.PageItem
import org.hrsh.story_kit.presentation.story.StoryViewModel

@Composable
fun PageVideo(
    itemVideo: PageItem.Video, pageSize: Float
) {
    Box(modifier = Modifier.graphicsLayer {
        scaleX = pageSize
        scaleY = pageSize
    }) {
        VideoPlayerScreen(Koin.di?.koin?.get<VideoPlayer>()!!, itemVideo.videoUrl)
    }
}

@Composable
fun VideoPlayerScreen(videoPlayer: VideoPlayer, videoUrl: String) {

    LaunchedEffect(videoUrl) {
        videoPlayer.play(videoUrl)
    }

    DisposableEffect(Unit) {
        onDispose {
            videoPlayer.stop()
            videoPlayer.release()
        }
    }
}