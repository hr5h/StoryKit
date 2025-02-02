package org.hrsh.story_kit.di

import org.hrsh.story_kit.domain.usecases.DeleteAllStoryUseCase
import org.hrsh.story_kit.domain.usecases.DeleteAllStoryUseCaseImpl
import org.hrsh.story_kit.domain.usecases.DeleteStoryUseCase
import org.hrsh.story_kit.domain.usecases.DeleteStoryUseCaseImpl
import org.hrsh.story_kit.domain.usecases.InsertStoryUseCase
import org.hrsh.story_kit.domain.usecases.InsertStoryUseCaseImpl
import org.hrsh.story_kit.domain.usecases.SubscribeStoryUseCase
import org.hrsh.story_kit.domain.usecases.SubscribeStoryUseCaseImpl
import org.hrsh.story_kit.domain.usecases.UpdateStoryUseCase
import org.hrsh.story_kit.domain.usecases.UpdateStoryUseCaseImpl
import org.koin.core.module.Module
import org.koin.dsl.module

fun domainModule(): Module {
    return module {
        single<SubscribeStoryUseCase> { SubscribeStoryUseCaseImpl(get()) }
        single<InsertStoryUseCase> { InsertStoryUseCaseImpl(get()) }
        single<UpdateStoryUseCase> { UpdateStoryUseCaseImpl(get()) }
        single<DeleteStoryUseCase> { DeleteStoryUseCaseImpl(get()) }
        single<DeleteAllStoryUseCase> { DeleteAllStoryUseCaseImpl(get()) }
    }
}