package org.hrsh.story_kit.data.mappers

import org.hrsh.story_kit.data.entities.StoryItemDb
import org.hrsh.story_kit.domain.entities.StoryItem

internal class StoryItemDbToDomainMapper(
    private val pageItemDbToDomainMapper: PageItemDbToDomainMapper
) : (StoryItemDb) -> StoryItem {
    override fun invoke(storyItemDb: StoryItemDb): StoryItem {
        return StoryItem(
            id = storyItemDb.id,
            imagePreview = storyItemDb.imagePreview,
            listPages = storyItemDb.listPages.mapNotNull(pageItemDbToDomainMapper),
            toDate = storyItemDb.toDate,
            isStartStory = storyItemDb.isStartStory,
            isLike = storyItemDb.isLike,
            countLike = storyItemDb.countLike,
            isShowInMiniature = storyItemDb.isShowInMiniature,
            isFavorite = storyItemDb.isFavorite,
            isViewed = storyItemDb.isViewed
        )
    }

}