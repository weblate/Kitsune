package io.github.drumber.kitsune.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.github.drumber.kitsune.data.source.local.library.LocalLibraryConverters
import io.github.drumber.kitsune.data.source.local.library.dao.LibraryEntryDao
import io.github.drumber.kitsune.data.source.local.library.dao.LibraryEntryModificationDao
import io.github.drumber.kitsune.data.source.local.library.dao.LibraryEntryWithModificationDao
import io.github.drumber.kitsune.data.source.local.library.dao.RemoteKeyDao
import io.github.drumber.kitsune.data.source.local.library.model.LocalLibraryEntry
import io.github.drumber.kitsune.data.source.local.library.model.LocalLibraryEntryModification
import io.github.drumber.kitsune.data.source.local.library.model.RemoteKeyEntity

@Database(
    entities = [
        LocalLibraryEntry::class, LocalLibraryEntryModification::class, RemoteKeyEntity::class
    ],
    version = 3,
    exportSchema = true
)
@TypeConverters(LocalLibraryConverters::class)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun libraryEntryDao(): LibraryEntryDao
    abstract fun libraryEntryModificationDao(): LibraryEntryModificationDao
    abstract fun libraryEntryWithModification(): LibraryEntryWithModificationDao
    abstract fun remoteKeyDao(): RemoteKeyDao

}