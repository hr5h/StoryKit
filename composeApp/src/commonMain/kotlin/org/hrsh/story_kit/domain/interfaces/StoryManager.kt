package org.hrsh.story_kit.domain.interfaces

import kotlinx.coroutines.flow.Flow
import org.hrsh.story_kit.domain.entities.StoryItem
import org.hrsh.story_kit.presentation.story.StoryColors

/**
 * Interface that defines the operations for managing story items.
 */
interface StoryManager {

    /**
     * Adds a new story item.
     *
     * @param storyItem The story item to be added
     * @param callback Callback that receives the result of the operation
     * The callback provides a [Result] object containing:
     * - [Result.success] with [Unit] if the operation completed successfully
     * - [Result.failure] with an exception if the operation failed
     */
    fun addStory(storyItem: StoryItem, callback: (Result<Unit>) -> Unit = {})

    /**
     * Updates an existing story item.
     *
     * @param storyItem The story item to be updated
     */
    fun updateStory(storyItem: StoryItem)

    /**
     * Deletes a story item.
     *
     * @param id The unique identifier of the story item to be deleted
     */
    fun deleteStory(id: Long)

    /**
     * Deletes all story items.
     */
    fun deleteAllStory()

    /**
     * Subscribes to changes in the history view status.
     *
     * @return A [Flow], which outputs a long history ID when viewing the history
     */
    fun subscribeStoryView(): Flow<Long>

    /**
     * Subscribes to changes in the like state of a story.
     *
     * @return A [Flow] that emits a Pair containing the story's ID and a Boolean indicating whether the story is liked
     */
    fun subscribeStoryLike(): Flow<Pair<Long, Boolean>>

    /**
     * Subscribes to changes in the like state of a story.
     *
     * @return A [Flow] that emits a Pair containing the story's ID and a Boolean indicating whether the story is favorite
     */
    fun subscribeStoryFavorite(): Flow<Pair<Long, Boolean>>

    /**
     * Subscribes to story skip events, providing data about skipped story pages.
     *
     * @return A [Flow] that emits a [Triple] containing:
     * - The ID of the story that was skipped ([Long])
     * - The page index that was skipped ([Int])
     * - The playback progress at the moment of skipping ([Float] between 0f and 1f)
     */
    fun subscribeStorySkip(): Flow<Triple<Long, Int, Float>>

    /**
     * Subscribes to changes in the selected answer on a story's questionnaire page.
     *
     * @return A [Flow] that emits a [Triple] containing:
     * - The ID of the story
     * - The page number where the questionnaire is located
     * - The index of the selected answer
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

    /**
     * Sets the color scheme for story-related views.
     *
     * @param storyColors The [StoryColors] object containing color values to be applied
     */
    fun setColors(storyColors: StoryColors)
}