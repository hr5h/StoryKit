package org.hrsh.story_kit.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.hrsh.story_kit.data.entities.StoryItemDb

@Dao
internal interface StoryDao {
    @Query("SELECT * FROM ${StoryItemDb.STORY_TABLE_NAME}")
    fun getAll(): Flow<List<StoryItemDb>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(storyItemDb: StoryItemDb)

    @Update
    suspend fun update(storyItemDb: StoryItemDb)

    @Delete
    suspend fun delete(storyItemDb: StoryItemDb)

    @Query("DELETE FROM ${StoryItemDb.STORY_TABLE_NAME}")
    suspend fun deleteAll()
}