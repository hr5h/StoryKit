package org.hrsh.story_kit.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.hrsh.story_kit.data.StoryItemDb.Companion.STORY_TABLE_NAME

@Entity(tableName = STORY_TABLE_NAME)
data class StoryItemDb(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val imagePreview: String
) {
    companion object {
        const val STORY_TABLE_NAME = "story_table"
    }
}
