package org.hrsh.story_kit.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [StoryItemDb::class],
    version = 1,
    exportSchema = false,
)
abstract class StoryDatabase: RoomDatabase() {
    abstract fun storyDao(): StoryDao

    companion object {
        const val DATABASE_NAME = "story.db"
    }
}