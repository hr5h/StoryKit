package org.hrsh.story_kit.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.hrsh.story_kit.data.entities.StoryItemDb.Companion.STORY_TABLE_NAME


@Entity(tableName = STORY_TABLE_NAME)
data class StoryItemDb(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val imagePreview: String,
    val listPages: List<PageItemDb>,
    val isStartStory: Boolean,
    val isLike: Boolean,
    val countLike: Int,
    val isFavorite: Boolean,
    val isViewed: Boolean,
) {
    companion object {
        const val STORY_TABLE_NAME = "story_table"
    }
}