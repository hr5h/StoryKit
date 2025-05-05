package org.hrsh.story_kit.data.mappers

import org.hrsh.story_kit.data.entities.StoryItemDb
import org.hrsh.story_kit.domain.entities.StoryItem

internal class StoryItemDomainToDbMapper(
    private val pageItemDomainToDbMapper: PageItemDomainToDbMapper
) : (StoryItem) -> StoryItemDb {
    override fun invoke(storyItem: StoryItem): StoryItemDb {
        return StoryItemDb(
            id = storyItem.id,
            imagePreview = storyItem.imagePreview,
            listPages = storyItem.listPages.map(pageItemDomainToDbMapper),
            isStartStory = storyItem.isStartStory,
            isLike = storyItem.isLike,
            countLike = storyItem.countLike,
            isShowInMiniature = storyItem.isShowInMiniature,
            isFavorite = storyItem.isFavorite,
            isViewed = storyItem.isViewed
        )
    }

}