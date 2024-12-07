package org.hrsh.story_kit.domain.usecases

import org.hrsh.story_kit.data.StoryItemDb
import org.hrsh.story_kit.domain.interfaces.StoryRepository

interface InsertStoryUseCase {
    suspend fun invoke(storyItemDb: StoryItemDb)
}

class InsertStoryUseCaseImpl(
    private val storyRepository: StoryRepository
): InsertStoryUseCase {

    override suspend fun invoke(storyItemDb: StoryItemDb) {
        storyRepository.postStory(storyItemDb)
    }
}