package org.hrsh.story_kit.data

import org.hrsh.story_kit.domain.interfaces.StoryRepository

class StoryRepositoryImpl(
    private val database: StoryDatabase
): StoryRepository {
    private val storyDao: StoryDao by lazy {
        database.storyDao()
    }

    override suspend fun getStories(): List<StoryItemDb> {
        return storyDao.getAll()
    }

    override suspend fun postStory(storyItemDb: StoryItemDb) {
        storyDao.insert(storyItemDb)
    }

    override suspend fun deleteStory(storyItemDb: StoryItemDb) {
        storyDao.delete(storyItemDb)
    }
}