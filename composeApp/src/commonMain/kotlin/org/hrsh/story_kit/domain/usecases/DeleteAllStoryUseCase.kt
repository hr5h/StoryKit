package org.hrsh.story_kit.domain.usecases

import org.hrsh.story_kit.domain.interfaces.StoryRepository

interface DeleteAllStoryUseCase {
    suspend operator fun invoke()
}

class DeleteAllStoryUseCaseImpl(
    private val storyRepository: StoryRepository
): DeleteAllStoryUseCase {

    override suspend fun invoke() {
        storyRepository.deleteAllStory()
    }
}