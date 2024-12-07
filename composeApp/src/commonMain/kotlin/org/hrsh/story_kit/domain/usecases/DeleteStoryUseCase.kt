package org.hrsh.story_kit.domain.usecases

import org.hrsh.story_kit.data.StoryItemDb
import org.hrsh.story_kit.domain.interfaces.StoryRepository

interface DeleteStoryUseCase {
    suspend fun invoke(storyItemDb: StoryItemDb)
}

class DeleteStoryUseCaseImpl(
    private val storyRepository: StoryRepository
): DeleteStoryUseCase {

    override suspend fun invoke(storyItemDb: StoryItemDb) {
        storyRepository.deleteStory(storyItemDb)
    }
}