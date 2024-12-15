package org.hrsh.story_kit.domain.usecases

import org.hrsh.story_kit.data.entities.StoryItemDb
import org.hrsh.story_kit.domain.entities.StoryItem
import org.hrsh.story_kit.domain.interfaces.StoryRepository

interface SubscribeStoryUseCase {
    suspend operator fun invoke(): List<StoryItem>
}

class SubscribeStoryUseCaseImpl(
    private val storyRepository: StoryRepository
): SubscribeStoryUseCase {

    override suspend fun invoke(): List<StoryItem> {
        return storyRepository.getStories()
    }
}