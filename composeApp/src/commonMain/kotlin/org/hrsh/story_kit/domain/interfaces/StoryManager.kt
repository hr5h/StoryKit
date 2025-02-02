package org.hrsh.story_kit.domain.interfaces

import kotlinx.coroutines.flow.StateFlow
import org.hrsh.story_kit.domain.entities.StoryItem

/**
 * Interface that defines the operations for managing story items.
 */
interface StoryManager {

    /**
     * Adds a new story item.
     *
     * @param storyItem The story item to be added.
     */
    fun addStory(storyItem: StoryItem)

    /**
     * Updates an existing story item.
     *
     * @param storyItem The story item to be updated.
     */
    fun updateStory(storyItem: StoryItem)

    /**
     * Deletes a story item.
     *
     * @param storyItem The story item to be deleted.
     */
    fun deleteStory(storyItem: StoryItem)

    /**
     * Deletes all story items.
     */
    fun deleteAllStory()

    /**
     * Subscribes to changes in the visibility state of a story.
     *
     * @param id The unique identifier of the story.
     * @return A [StateFlow] that emits a boolean indicating the visibility state of the story.
     */
    fun subscribeStoryView(id: Long): StateFlow<Boolean>

    /**
     * Subscribes to changes in the like state of a story.
     *
     * @param id The unique identifier of the story.
     * @return A [StateFlow] that emits a boolean indicating whether the story is liked.
     */
    fun subscribeStoryLike(id: Long): StateFlow<Boolean>

    /**
     * Subscribes to changes in the skip state of a story.
     *
     * @param id The unique identifier of the story.
     * @return A [StateFlow] that emits a boolean indicating whether the story has been skipped.
     */
    fun subscribeStorySkip(id: Long): StateFlow<Boolean>
}