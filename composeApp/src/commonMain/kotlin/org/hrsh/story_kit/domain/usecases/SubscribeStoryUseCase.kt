package org.hrsh.story_kit.domain.usecases

import kotlinx.coroutines.flow.Flow
import org.hrsh.story_kit.domain.entities.StoryItem
import org.hrsh.story_kit.domain.interfaces.StoryRepository

internal interface SubscribeStoryUseCase {
    suspend operator fun invoke(): Flow<List<StoryItem>>
}

internal class SubscribeStoryUseCaseImpl(
    private val storyRepository: StoryRepository
): SubscribeStoryUseCase {

    override suspend fun invoke(): Flow<List<StoryItem>> {
        return storyRepository.getStories()
    }
}