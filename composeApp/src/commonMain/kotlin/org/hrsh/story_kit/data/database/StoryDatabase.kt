package org.hrsh.story_kit.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.hrsh.story_kit.data.dao.StoryDao
import org.hrsh.story_kit.data.entities.StoryItemDb
import org.hrsh.story_kit.data.utils.PagesConverter

@Database(
    entities = [StoryItemDb::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(PagesConverter::class)
abstract class StoryDatabase: RoomDatabase() {
    abstract fun storyDao(): StoryDao

    companion object {
        const val DATABASE_NAME = "story.db"
    }
}