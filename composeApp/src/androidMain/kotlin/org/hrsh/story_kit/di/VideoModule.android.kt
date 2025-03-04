package org.hrsh.story_kit.di

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import org.koin.dsl.module

actual class VideoPlayer actual constructor() {
    private var exoPlayer: ExoPlayer? = null

    actual fun play(url: String) {
        val context = Koin.di?.koin?.get<Context>()!!
        exoPlayer = ExoPlayer.Builder(context).build()
        val mediaItem = MediaItem.fromUri(url)
        exoPlayer?.setMediaItem(mediaItem)
        exoPlayer?.prepare()
        exoPlayer?.play()
    }

    actual fun stop() {
        exoPlayer?.stop()
    }

    actual fun release() {
        exoPlayer?.release()
        exoPlayer = null
    }
}

actual fun videoPlayerModule() = module {
    single<VideoPlayer> { VideoPlayer() }
}