package org.hrsh.story_kit.domain.interfaces

import org.hrsh.story_kit.data.entities.StoryItemDb
import org.hrsh.story_kit.domain.entities.StoryItem

interface StoryRepository {

    suspend fun getStories(): List<StoryItem>
    suspend fun postStory(storyItem: StoryItem)
    suspend fun updateStory(storyItem: StoryItem)
    suspend fun deleteStory(storyItem: StoryItem)
}