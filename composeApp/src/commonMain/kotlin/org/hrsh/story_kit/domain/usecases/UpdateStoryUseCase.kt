package org.hrsh.story_kit.domain.usecases

import org.hrsh.story_kit.domain.entities.StoryItem
import org.hrsh.story_kit.domain.interfaces.StoryRepository

internal interface UpdateStoryUseCase {
    suspend operator fun invoke(storyItem: StoryItem)
}

internal class UpdateStoryUseCaseImpl(
    private val storyRepository: StoryRepository
): UpdateStoryUseCase {

    override suspend fun invoke(storyItem: StoryItem) {
        storyRepository.updateStory(storyItem)
    }
}