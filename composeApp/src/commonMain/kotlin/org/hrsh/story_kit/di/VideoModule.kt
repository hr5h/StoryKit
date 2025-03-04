package org.hrsh.story_kit.di

import org.koin.core.module.Module

expect class VideoPlayer() {
    fun play(url: String)
    fun stop()
    fun release()
}

expect fun videoPlayerModule(): Module