package org.hrsh.story_kit.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StoryDao {
    @Query("SELECT * FROM ${StoryItemDb.STORY_TABLE_NAME}")
    suspend fun getAll(): List<StoryItemDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteDb: StoryItemDb)

    @Delete
    suspend fun delete(favoriteDb: StoryItemDb)
}