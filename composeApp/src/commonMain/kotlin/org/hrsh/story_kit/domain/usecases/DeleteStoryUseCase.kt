package org.hrsh.story_kit.domain.usecases

import org.hrsh.story_kit.domain.entities.StoryItem
import org.hrsh.story_kit.domain.interfaces.StoryRepository

internal interface DeleteStoryUseCase {
    suspend operator fun invoke(storyItem: StoryItem)
}

internal class DeleteStoryUseCaseImpl(
    private val storyRepository: StoryRepository
): DeleteStoryUseCase {

    override suspend fun invoke(storyItem: StoryItem) {
        storyRepository.deleteStory(storyItem)
    }
}