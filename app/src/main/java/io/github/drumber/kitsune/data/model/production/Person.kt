package io.github.drumber.kitsune.data.model.production

import android.os.Parcelable
import com.github.jasminb.jsonapi.annotations.Id
import com.github.jasminb.jsonapi.annotations.Type
import io.github.drumber.kitsune.data.model.media.Image
import kotlinx.parcelize.Parcelize

@Parcelize
@Type("people")
data class Person(
    @Id val id: String?,
    val name: String?,
    val description: String?,
    val image: Image?
) : Parcelable
