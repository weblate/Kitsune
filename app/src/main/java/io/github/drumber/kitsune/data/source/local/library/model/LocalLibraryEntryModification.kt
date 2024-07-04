package io.github.drumber.kitsune.data.source.local.library.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.drumber.kitsune.data.source.local.library.model.LocalLibraryModificationState.NOT_SYNCHRONIZED

@Entity(tableName = "library_entries_modifications")
data class LocalLibraryEntryModification(
    /** Corresponds to the library entry ID */
    @PrimaryKey val id: String,

    val createTime: Long = System.currentTimeMillis(),
    val state: LocalLibraryModificationState = NOT_SYNCHRONIZED,

    val startedAt: String?,
    val finishedAt: String?,

    val status: LocalLibraryStatus?,
    val progress: Int?,
    val reconsumeCount: Int?,
    val volumesOwned: Int?,
    /**  Set to `-1` to remove rating (will be mapped to `null` by the json serializer) */
    val ratingTwenty: Int?,

    val notes: String?,
    val privateEntry: Boolean?
)
