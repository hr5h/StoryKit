package org.hrsh.story_kit.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import org.hrsh.story_kit.data.database.StoryDatabase
import org.koin.dsl.module

fun getDatabaseBuilder(): RoomDatabase.Builder<StoryDatabase> {
    val dbFilePath = NSHomeDirectory() + "/" + StoryDatabase.DATABASE_NAME
    return Room.databaseBuilder<StoryDatabase>(
        name = dbFilePath,
        factory =  { StoryDatabase::class.instantiateImpl() }
    )
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
}

fun getDatabase(): StoryDatabase {
    return getDatabaseBuilder().build()
}

actual fun databaseModule() = module {
    single<StoryDatabase> { getDatabase() }
}