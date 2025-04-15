package org.hrsh.story_kit.domain.usecases

import org.hrsh.story_kit.domain.interfaces.StoryRepository

internal interface DeleteAllStoryUseCase {
    suspend operator fun invoke()
}

internal class DeleteAllStoryUseCaseImpl(
    private val storyRepository: StoryRepository
): DeleteAllStoryUseCase {

    override suspend fun invoke() {
        storyRepository.deleteAllStory()
    }
}