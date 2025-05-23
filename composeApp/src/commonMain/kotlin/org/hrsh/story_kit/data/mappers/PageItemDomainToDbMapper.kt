package org.hrsh.story_kit.data.mappers

import org.hrsh.story_kit.data.entities.PageItemDb
import org.hrsh.story_kit.data.entities.PageType
import org.hrsh.story_kit.domain.entities.PageItem

internal class PageItemDomainToDbMapper() : (PageItem) -> PageItemDb {
    override fun invoke(pageItem: PageItem): PageItemDb {
        return when (pageItem) {
            is PageItem.Image -> PageItemDb(
                type = PageType.IMAGE,
                imageUrl = pageItem.imageUrl,
                text = pageItem.text,
                timeShow = pageItem.timeShow
            )

            is PageItem.Video -> PageItemDb(
                type = PageType.VIDEO,
                videoUrl = pageItem.videoUrl,
                timeShow = pageItem.timeShow
            )

            is PageItem.Question -> PageItemDb(
                type = PageType.QUESTION,
                imageUrl = pageItem.imageUrl,
                question = pageItem.question,
                listAnswers = pageItem.listAnswers,
                listResults = pageItem.listResults,
                indexSelected = pageItem.indexSelected,
                timeShow = pageItem.timeShow
            )

            is PageItem.Error -> TODO()
            is PageItem.Game -> TODO()
        }
    }

}