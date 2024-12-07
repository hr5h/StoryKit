package org.hrsh.story_kit.domain.usecases

import org.hrsh.story_kit.data.StoryItemDb
import org.hrsh.story_kit.domain.interfaces.StoryRepository

interface SubscribeStoryUseCase {
    suspend fun invoke(): List<StoryItemDb>
}

class SubscribeStoryUseCaseImpl(
    private val storyRepository: StoryRepository
): SubscribeStoryUseCase {

    override suspend fun invoke(): List<StoryItemDb> {
        return storyRepository.getStories()
    }
}