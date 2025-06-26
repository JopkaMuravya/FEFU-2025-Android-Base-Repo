package co.feip.fefu2025.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JikanResponse<T>(
    val data: T
)

@Serializable
data class JikanAnimeDto(
    @SerialName("mal_id") val malId: Int,
    val title: String,
    val images: JikanImagesDto,
    val score: Float? = null,
    val episodes: Int? = null,
    val year: Int? = null,
    val synopsis: String? = null,
    val genres: List<JikanGenreDto> = emptyList()
)

@Serializable
data class JikanImagesDto(
    val jpg: JikanImageUrlsDto,
    val webp: JikanImageUrlsDto
)

@Serializable
data class JikanImageUrlsDto(
    @SerialName("image_url") val imageUrl: String,
    @SerialName("small_image_url") val smallImageUrl: String,
    @SerialName("large_image_url") val largeImageUrl: String
)

@Serializable
data class JikanGenreDto(
    val mal_id: Int,
    val type: String,
    val name: String,
    val url: String
)

@Serializable
data class JikanRecommendationDto(
    val entry: JikanAnimeDto
)

@Serializable
data class JikanStatisticsDto(
    val scores: List<JikanScoreDto> = emptyList()
)

@Serializable
data class JikanScoreDto(
    val score: Int,
    val votes: Int,
    val percentage: Float
)