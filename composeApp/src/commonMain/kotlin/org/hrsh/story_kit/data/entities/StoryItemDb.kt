package org.hrsh.story_kit.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.hrsh.story_kit.data.entities.StoryItemDb.Companion.STORY_TABLE_NAME


@Entity(tableName = STORY_TABLE_NAME)
internal data class StoryItemDb(
    @PrimaryKey
    val id: Long,
    val imagePreview: String,
    val listPages: List<PageItemDb>,
    val isStartStory: Boolean,
    val isLike: Boolean,
    val countLike: Int,
    val isShowInMiniature: Boolean,
    val isFavorite: Boolean,
    val isViewed: Boolean,
) {
    companion object {
        const val STORY_TABLE_NAME = "story_table"
    }
}