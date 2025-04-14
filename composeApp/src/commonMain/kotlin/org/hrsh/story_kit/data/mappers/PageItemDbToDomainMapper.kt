package org.hrsh.story_kit.data.mappers

import org.hrsh.story_kit.data.entities.PageItemDb
import org.hrsh.story_kit.data.entities.PageType
import org.hrsh.story_kit.domain.entities.PageItem

internal class PageItemDbToDomainMapper() : (PageItemDb) -> PageItem? {
    override fun invoke(pageItemDb: PageItemDb): PageItem? {
        return when (pageItemDb.type) {
            PageType.IMAGE -> PageItem.Image(
                imageUrl = pageItemDb.imageUrl ?: return null,
                text = pageItemDb.text ?: return null,
                timeShow = pageItemDb.timeShow ?: 5f
            )

            PageType.VIDEO -> PageItem.Video(
                videoUrl = pageItemDb.videoUrl ?: return null,
                timeShow = pageItemDb.timeShow ?: 5f
            )

            PageType.QUESTION -> PageItem.Question(
                imageUrl = pageItemDb.imageUrl ?: return null,
                question = pageItemDb.question ?: return null,
                listAnswers = pageItemDb.listAnswers ?: return null,
                listResults = pageItemDb.listResults ?: return null,
                indexSelected = pageItemDb.indexSelected ?: return null,
                timeShow = pageItemDb.timeShow ?: 5f
            )
        }
    }

}