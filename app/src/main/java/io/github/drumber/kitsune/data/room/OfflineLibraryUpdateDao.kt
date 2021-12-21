package io.github.drumber.kitsune.data.room

import androidx.room.*
import io.github.drumber.kitsune.data.model.library.OfflineLibraryUpdate

@Dao
interface OfflineLibraryUpdateDao {

    @Query("SELECT * FROM offline_library_update WHERE id = :id")
    suspend fun getOfflineLibraryUpdate(id: String): OfflineLibraryUpdate?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSingle(offlineLibraryUpdate: OfflineLibraryUpdate)

    @Update
    suspend fun updateOfflineLibraryUpdate(offlineLibraryUpdate: OfflineLibraryUpdate)

    @Delete
    suspend fun deleteOfflineLibraryUpdate(offlineLibraryUpdate: OfflineLibraryUpdate)

    @Query("DELETE FROM offline_library_update")
    suspend fun clearOfflineLibraryUpdates()

}