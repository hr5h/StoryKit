package org.hrsh.story_kit.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import org.hrsh.story_kit.data.StoryDatabase
import org.koin.dsl.module

fun getDatabaseBuilder(ctx: Context): RoomDatabase.Builder<StoryDatabase> {
    val appContext = ctx.applicationContext
    val dbFile = appContext.getDatabasePath(StoryDatabase.DATABASE_NAME)
    return Room.databaseBuilder<StoryDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
}

fun getDatabase(ctx: Context): StoryDatabase {
    return getDatabaseBuilder(ctx).build()
}

actual fun databaseModule() = module {
    single<StoryDatabase> { getDatabase(get()) }
}