package org.hrsh.story_kit.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import org.hrsh.story_kit.data.entities.StoryItemDb
import org.hrsh.story_kit.domain.entities.StoryItem

@Dao
interface StoryDao {
    @Query("SELECT * FROM ${StoryItemDb.STORY_TABLE_NAME}")
    suspend fun getAll(): List<StoryItemDb>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(storyItemDb: StoryItemDb)

    @Update
    suspend fun update(storyItemDb: StoryItemDb)

    @Delete
    suspend fun delete(storyItemDb: StoryItemDb)
}