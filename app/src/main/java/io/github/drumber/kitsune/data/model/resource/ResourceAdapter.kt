package io.github.drumber.kitsune.data.model.resource

import android.content.Context
import android.os.Parcelable
import io.github.drumber.kitsune.R
import io.github.drumber.kitsune.preference.KitsunePref
import io.github.drumber.kitsune.preference.TitlesPref
import io.github.drumber.kitsune.util.originalOrDown
import io.github.drumber.kitsune.util.smallOrHigher
import io.github.drumber.kitsune.util.toDate
import kotlinx.parcelize.Parcelize
import java.util.*

sealed class ResourceAdapter(
    val title: String,
    val description: String,
    val startDate: String?,
    val avgRating: String,
    val userCount: Int,
    val favoriteCount: Int,
    val popularityRank: Int,
    val ratingRank: Int,
    val ageRating: AgeRating?,
    val ageRatingGuide: String?,
    val subtype: String,
    val status: Status?,
    val tba: String?,
    val posterImage: String?,
    val coverImage: String?
) : Parcelable {

    val publishingYear: String
        get() = if (!startDate.isNullOrBlank()) {
            startDate.toDate("yyyy-MM-dd").get(Calendar.YEAR).toString()
        } else "?"

    inline fun statusText(context: Context): String {
        return when (status) {
            Status.current -> context.getString(R.string.status_current)
            Status.finished -> context.getString(R.string.status_finished)
            Status.tba -> context.getString(R.string.status_tba)
            Status.unreleased -> context.getString(R.string.status_unreleased)
            Status.upcoming -> context.getString(R.string.status_upcoming)
            null -> context.getString(R.string.no_information)
        }
    }

    @Parcelize
    class AnimeResource(val anime: Anime) : ResourceAdapter(
        title = getTitle(anime.titles, anime.canonicalTitle),
        description = anime.description.orEmpty(),
        startDate = anime.startDate,
        avgRating = anime.averageRating.orEmpty(),
        userCount = anime.userCount.orNull(),
        favoriteCount = anime.favoritesCount.orNull(),
        popularityRank = anime.popularityRank.orNull(),
        ratingRank = anime.ratingRank.orNull(),
        ageRating = anime.ageRating,
        ageRatingGuide = anime.ageRatingGuide,
        subtype = anime.subtype?.name.orEmpty(),
        status = anime.status,
        tba = anime.tba,
        posterImage = anime.posterImage?.smallOrHigher(),
        coverImage = anime.coverImage?.originalOrDown()
    ), Parcelable

    @Parcelize
    class MangaResource(val manga: Manga) : ResourceAdapter(
        title = getTitle(manga.titles, manga.canonicalTitle),
        description = manga.description.orEmpty(),
        startDate = manga.startDate,
        avgRating = manga.averageRating.orEmpty(),
        userCount = manga.userCount.orNull(),
        favoriteCount = manga.favoritesCount.orNull(),
        popularityRank = manga.popularityRank.orNull(),
        ratingRank = manga.ratingRank.orNull(),
        ageRating = manga.ageRating,
        ageRatingGuide = manga.ageRatingGuide,
        subtype = manga.subtype?.name.orEmpty(),
        status = manga.status,
        tba = manga.tba,
        posterImage = manga.posterImage?.smallOrHigher(),
        coverImage = manga.coverImage?.originalOrDown()
    ), Parcelable

    companion object {
        fun fromResource(resource: Resource) = when (resource) {
            is Anime -> AnimeResource(resource)
            is Manga -> MangaResource(resource)
        }
    }

}

private fun getTitle(title: Titles?, canonical: String?): String {
    val nf = "<No title found>"
    return when (KitsunePref.titles) {
        TitlesPref.Canoncial -> canonical ?: nf
        TitlesPref.Romanized -> title?.enJp ?: canonical ?: nf
        TitlesPref.English -> title?.en ?: canonical ?: nf
    }
}

private inline fun Int?.orNull() = this ?: 0
