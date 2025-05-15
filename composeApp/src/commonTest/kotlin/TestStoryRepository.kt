package org.hrsh.story_kit.domain.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.hrsh.story_kit.domain.entities.StoryItem
import org.hrsh.story_kit.domain.interfaces.StoryRepository

class TestStoryRepository : StoryRepository {
    var postedStory: StoryItem? = null
    val stories = mutableListOf<StoryItem>()

    override suspend fun getStories(): Flow<List<StoryItem>> {
        return flow { emit(stories.toList()) }
    }

    override suspend fun insertStory(storyItem: StoryItem): Result<Unit> {
        postedStory = storyItem
        stories.add(storyItem)
        return Result.success(Unit)
    }

    override suspend fun updateStory(storyItem: StoryItem) {
        val index = stories.indexOfFirst { it.id == storyItem.id }
        if (index != -1) {
            stories[index] = storyItem
        }
    }

    override suspend fun deleteStory(storyItem: StoryItem) {
        stories.remove(storyItem)
    }

    override suspend fun deleteAllStory() {
        stories.clear()
    }
}