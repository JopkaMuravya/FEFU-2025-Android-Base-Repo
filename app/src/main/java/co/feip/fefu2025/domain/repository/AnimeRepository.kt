package co.feip.fefu2025.domain.repository

import co.feip.fefu2025.domain.models.AnimeCard
import co.feip.fefu2025.domain.models.AnimeDetails
import co.feip.fefu2025.domain.models.RatingData

interface AnimeRepository {
    suspend fun getAnimeCards(
        page: Int,
        limit: Int
    ): List<AnimeCard>
    suspend fun getAnimeDetailsById(id: Int): AnimeDetails?
    suspend fun searchAnime(
        query: String,
        page: Int,
        limit: Int
    ): List<AnimeCard>
    suspend fun getAnimeCardById(id: Int): AnimeCard?
    suspend fun getAllRecommendationsForId(animeId: Int): List<AnimeCard>
    suspend fun getAnimeStatisticsById(id: Int): List<RatingData>
}