package org.hrsh.story_kit.di


import AVFoundation.AVPlayer
import AVFoundation.AVPlayerItem
import org.koin.core.module.Module

actual class VideoPlayer actual constructor() {
    private var avPlayer: AVPlayer? = null

    actual fun play(url: String) {
        val nsUrl = NSURL(string = url)
        avPlayer = AVPlayer(url = nsUrl)
        avPlayer?.play()
    }

    actual fun stop() {
        avPlayer?.pause()
    }

    actual fun release() {
        avPlayer = null
    }
}

actual fun videoPlayerModule(): Module {
    TODO("Not yet implemented")
}