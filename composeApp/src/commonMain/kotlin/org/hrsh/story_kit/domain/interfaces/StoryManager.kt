package org.hrsh.story_kit.domain.interfaces

import kotlinx.coroutines.flow.StateFlow
import org.hrsh.story_kit.domain.entities.StoryItem

internal interface StoryManager {
    fun addStory(storyItem: StoryItem)
    fun updateStory(storyItem: StoryItem)
    fun deleteStory(storyItem: StoryItem)
    fun subscribeStoryView(id: Long): StateFlow<Boolean>
    fun subscribeStoryLike(id: Long): StateFlow<Boolean>
    fun subscribeStorySkip(id: Long): StateFlow<Boolean>
}