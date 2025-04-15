package org.hrsh.story_kit.domain.interfaces

import kotlinx.coroutines.flow.Flow
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
     * @param id The unique identifier of the story item to be deleted.
     */
    fun deleteStory(id: Long)

    /**
     * Deletes all story items.
     */
    fun deleteAllStory()

    /**
     * Subscribes to changes in the history view status.
     *
     * @return A [Flow], which outputs a long history ID when viewing the history.
     */
    fun subscribeStoryView(): Flow<Long>

    /**
     * Subscribes to changes in the like state of a story.
     *
     * @return A [Flow] that emits a Pair containing the story's ID and a Boolean indicating whether the story is liked.
     */
    fun subscribeStoryLike(): Flow<Pair<Long, Boolean>>

    /**
     * Subscribes to changes in the like state of a story.
     *
     * @return A [Flow] that emits a Pair containing the story's ID and a Boolean indicating whether the story is favorite.
     */
    fun subscribeStoryFavorite(): Flow<Pair<Long, Boolean>>

    /**
     * Subscribes to changes in the skip state of a story.
     *
     * @return A [Flow] that emits a Pair containing the story's ID and a Boolean indicating whether the story has been skipped.
     */
    fun subscribeStorySkip(): Flow<Pair<Long, Boolean>>

    /**
     * Subscribes to changes in the selected answer on a story's questionnaire page.
     *
     * @return A [Flow] that emits a [Triple] containing:
     * - The ID of the story.
     * - The page number where the questionnaire is located.
     * - The index of the selected answer.
     */
    fun subscribeStoryQuestion(): Flow<Triple<Long, Int, Int>>

    /**
     * Subscribes to changes in the pause state of a story playback.
     *
     * @return A [Flow] that emits a Boolean value indicating whether the story is currently paused:
     * - `true` if story playback is paused
     * - `false` if story playback is active/resumed
     */
    fun subscribeStoryPause(): Flow<Boolean>
}