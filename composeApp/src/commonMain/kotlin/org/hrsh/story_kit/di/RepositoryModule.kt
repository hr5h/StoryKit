package org.hrsh.story_kit.di

import org.hrsh.story_kit.data.repositories.StoryRepositoryImpl
import org.hrsh.story_kit.domain.interfaces.StoryRepository
import org.koin.core.module.Module
import org.koin.dsl.module

fun repositoryModule() : Module {
    return module {
        single<StoryRepository> { StoryRepositoryImpl(get(), get(), get()) }
    }
}