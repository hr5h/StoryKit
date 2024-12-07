package org.hrsh.story_kit.domain.interfaces

import org.hrsh.story_kit.data.StoryItemDb

interface StoryRepository {

    suspend fun getStories(): List<StoryItemDb>
    suspend fun postStory(storyItemDb: StoryItemDb)
    suspend fun deleteStory(storyItemDb: StoryItemDb)
}