package org.hrsh.story_kit.di

import org.hrsh.story_kit.data.mappers.PageItemDbToDomainMapper
import org.hrsh.story_kit.data.mappers.PageItemDomainToDbMapper
import org.hrsh.story_kit.data.mappers.StoryItemDbToDomainMapper
import org.hrsh.story_kit.data.mappers.StoryItemDomainToDbMapper
import org.koin.core.module.Module
import org.koin.dsl.module

internal fun mapperModule(): Module {
    return module {
        single { PageItemDbToDomainMapper() }
        single { PageItemDomainToDbMapper() }
        single{ StoryItemDbToDomainMapper(get()) }
        single{ StoryItemDomainToDbMapper(get()) }
    }
}