package org.hrsh.story_kit.domain.usecases

import org.hrsh.story_kit.domain.entities.StoryItem
import org.hrsh.story_kit.domain.interfaces.StoryRepository

internal interface InsertStoryUseCase {
    suspend operator fun invoke(storyItem: StoryItem)
}

internal class InsertStoryUseCaseImpl(
    private val storyRepository: StoryRepository
): InsertStoryUseCase {

    override suspend fun invoke(storyItem: StoryItem) {
        storyRepository.postStory(storyItem)
    }
}