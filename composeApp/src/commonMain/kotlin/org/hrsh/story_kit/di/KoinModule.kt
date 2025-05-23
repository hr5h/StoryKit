package org.hrsh.story_kit.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

internal fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(databaseModule(), repositoryModule(), mapperModule(), domainModule(), storyViewModelModule(), navigatorModule())
    }

internal object Koin {
    var di: KoinApplication? = null

    fun setupKoin(appDeclaration: KoinAppDeclaration = {}) {
        if (di == null) {
            di = initKoin(appDeclaration)
        }
    }
}