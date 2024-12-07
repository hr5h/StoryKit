package org.hrsh.story_kit.di

import org.hrsh.story_kit.presentation.story.StoryViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

fun storyViewModelModule(): Module {
    return module {
        factory { StoryViewModel(get(), get(), get()) }
    }
}