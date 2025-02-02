package org.hrsh.story_kit.domain.interfaces

import kotlinx.coroutines.flow.Flow
import org.hrsh.story_kit.domain.entities.StoryItem

interface StoryRepository {

    suspend fun getStories(): Flow<List<StoryItem>>
    suspend fun postStory(storyItem: StoryItem)
    suspend fun updateStory(storyItem: StoryItem)
    suspend fun deleteStory(storyItem: StoryItem)
    suspend fun deleteAllStory()
}