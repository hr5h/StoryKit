package org.hrsh.story_kit.domain.usecases

import org.hrsh.story_kit.domain.entities.StoryItem
import org.hrsh.story_kit.domain.interfaces.StoryRepository

interface DeleteStoryUseCase {
    suspend operator fun invoke(storyItem: StoryItem)
}

class DeleteStoryUseCaseImpl(
    private val storyRepository: StoryRepository
): DeleteStoryUseCase {

    override suspend fun invoke(storyItem: StoryItem) {
        storyRepository.deleteStory(storyItem)
    }
}