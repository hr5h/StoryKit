package org.hrsh.story_kit.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import org.hrsh.story_kit.data.entities.PageItemDb.Companion.STORY_TABLE_NAME

@Entity(tableName = STORY_TABLE_NAME)
@Serializable
internal data class PageItemDb(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val type: PageType,
    val timeShow: Float? = null,
    val imageUrl: String? = null,
    val text: String? = null,
    val videoUrl: String? = null,
    val question: String? = null,
    val listAnswers: List<String>? = null,
    val listResults: List<Int>? = null,
    val indexSelected: Int? = null,
) {
    companion object {
        const val STORY_TABLE_NAME = "story_table"
    }
}

internal enum class PageType {
    IMAGE, VIDEO, QUESTION
}