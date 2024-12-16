package org.hrsh.story_kit.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.hrsh.story_kit.data.dao.StoryDao
import org.hrsh.story_kit.data.database.StoryDatabase
import org.hrsh.story_kit.data.mappers.StoryItemDbToDomainMapper
import org.hrsh.story_kit.data.mappers.StoryItemDomainToDbMapper
import org.hrsh.story_kit.domain.entities.StoryItem
import org.hrsh.story_kit.domain.interfaces.StoryRepository

class StoryRepositoryImpl(
    private val database: StoryDatabase,
    private val storyItemDbToDomainMapper: StoryItemDbToDomainMapper,
    private val storyItemDomainToDbMapper: StoryItemDomainToDbMapper
) : StoryRepository {
    private val storyDao: StoryDao by lazy {
        database.storyDao()
    }

    override suspend fun getStories(): Flow<List<StoryItem>> {
        return storyDao.getAll().map { it.map(storyItemDbToDomainMapper) }
    }

    override suspend fun postStory(storyItem: StoryItem) {
        val storyItemDb = storyItemDomainToDbMapper(storyItem)
        storyDao.insert(storyItemDb)
    }

    override suspend fun updateStory(storyItem: StoryItem) {
        val storyItemDb = storyItemDomainToDbMapper(storyItem)
        storyDao.update(storyItemDb)
    }

    override suspend fun deleteStory(storyItem: StoryItem) {
        val storyItemDb = storyItemDomainToDbMapper(storyItem)
        storyDao.delete(storyItemDb)
    }
}