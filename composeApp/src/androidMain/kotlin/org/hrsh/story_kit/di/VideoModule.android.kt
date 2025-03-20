package org.hrsh.story_kit.di

import android.net.Uri
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
actual fun VideoPlayer(modifier: Modifier, url: String, isVisible: Boolean) {
    val context = LocalContext.current
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    val player = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri(Uri.parse(url))
            setMediaItem(mediaItem)
            playWhenReady = true
        }
    }

    LaunchedEffect(isVisible) {
        if (isVisible) {
            player.prepare()
            player.play()
        } else {
            player.stop()
            player.seekTo(0)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            player.release()
        }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                player.play()
            } else if (event == Lifecycle.Event.ON_STOP) {
                player.pause()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    AndroidView(
        factory = {
            PlayerView(context).apply {
                setUseController(false)
            }
        },
        modifier = modifier.then(Modifier.pointerInput(Unit) {
            detectTapGestures(
                onLongPress = {
                    player.pause()
                },
                onPress = {
                    if (tryAwaitRelease()) {
                        player.play()
                    }
                }
            )
        }),
        update = { playerView ->
            playerView.player = player
        }
    )
}