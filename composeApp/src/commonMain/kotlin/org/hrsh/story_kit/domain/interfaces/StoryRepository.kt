package org.hrsh.story_kit.domain.interfaces

import kotlinx.coroutines.flow.Flow
import org.hrsh.story_kit.domain.entities.StoryItem

internal interface StoryRepository {

    suspend fun getStories(): Flow<List<StoryItem>>
    suspend fun insertStory(storyItem: StoryItem): Result<Unit>
    suspend fun updateStory(storyItem: StoryItem)
    suspend fun deleteStory(storyItem: StoryItem)
    suspend fun deleteAllStory()
}