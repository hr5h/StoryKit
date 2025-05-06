package org.hrsh.story_kit.di

import androidx.compose.runtime.Composable
import org.koin.core.module.Module
import org.koin.dsl.module

expect class Navigator() {

    @Composable
    fun navigateToStory()

    fun finishStory()
}

internal fun navigatorModule(): Module {
    return module {
        single { Navigator() }
    }
}