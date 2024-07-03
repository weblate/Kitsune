package io.github.drumber.kitsune.data.presentation.model.media

import io.github.drumber.kitsune.data.common.Image
import io.github.drumber.kitsune.data.common.Titles
import io.github.drumber.kitsune.data.common.media.AgeRating
import io.github.drumber.kitsune.data.presentation.model.media.category.Category
import io.github.drumber.kitsune.data.presentation.model.media.relationship.MediaRelationship
import io.github.drumber.kitsune.data.presentation.model.user.FavoriteItem

sealed interface Media : FavoriteItem {
    val id: String
    val slug: String?

    val description: String?
    val titles: Titles?
    val canonicalTitle: String?
    val abbreviatedTitles: List<String>?

    val averageRating: String?
    val ratingFrequencies: RatingFrequencies?
    val userCount: Int?
    val favoritesCount: Int?
    val popularityRank: Int?
    val ratingRank: Int?

    val startDate: String?
    val endDate: String?
    val nextRelease: String?
    val tba: String?
    val status: ReleaseStatus?

    val ageRating: AgeRating?
    val ageRatingGuide: String?
    val nsfw: Boolean?

    val posterImage: Image?
    val coverImage: Image?

    val totalLength: Int?

    val categories: List<Category>?
    val mediaRelationships: List<MediaRelationship>?
}