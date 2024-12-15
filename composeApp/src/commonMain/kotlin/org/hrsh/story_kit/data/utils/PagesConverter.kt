package org.hrsh.story_kit.data.utils

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.hrsh.story_kit.data.entities.PageItemDb

class PagesConverter {
    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromPageItemDbList(value: List<PageItemDb>?): String? {
        return value?.let { json.encodeToString(it) }
    }

    @TypeConverter
    fun toPageItemDbList(value: String?): List<PageItemDb>? {
        return value?.let { json.decodeFromString<List<PageItemDb>>(it) }
    }
}