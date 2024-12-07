package org.hrsh.story_kit.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(databaseModule(), repositoryModule(), domainModule(), storyViewModelModule())
    }

object Koin {
    var di: KoinApplication? = null

    fun setupKoin(appDeclaration: KoinAppDeclaration = {}) {
        if (di == null) {
            di = initKoin(appDeclaration)
        }
    }
}