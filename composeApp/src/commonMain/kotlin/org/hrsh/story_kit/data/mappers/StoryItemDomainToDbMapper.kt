package org.hrsh.story_kit.data.mappers

import org.hrsh.story_kit.data.entities.StoryItemDb
import org.hrsh.story_kit.domain.entities.StoryItem

class StoryItemDomainToDbMapper(
    private val pageItemDomainToDbMapper: PageItemDomainToDbMapper
) : (StoryItem) -> StoryItemDb {
    override fun invoke(storyItem: StoryItem): StoryItemDb {
        return StoryItemDb(
            imagePreview = storyItem.imagePreview,
            listPages = storyItem.listPages.map(pageItemDomainToDbMapper),
            isStartStory = storyItem.isStartStory,
            isLike = storyItem.isLike,
            countLike = storyItem.countLike,
            isFavorite = storyItem.isFavorite,
            isViewed = storyItem.isViewed
        )
    }

}