package co.feip.fefu2025.data.repository

import co.feip.fefu2025.data.remote.RetrofitClient
import co.feip.fefu2025.data.remote.toAnimeCard
import co.feip.fefu2025.data.remote.toAnimeGenre
import co.feip.fefu2025.domain.models.AnimeCard
import co.feip.fefu2025.domain.models.AnimeDetails
import co.feip.fefu2025.domain.models.RatingData
import co.feip.fefu2025.domain.repository.AnimeRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class AnimeRepositoryImpl : AnimeRepository {

    private val apiService = RetrofitClient.api

    override suspend fun getAnimeCards(page: Int, limit: Int): List<AnimeCard> {
        val response = apiService.getTopAnime(page = page, limit = limit)
        return response.data.map { it.toAnimeCard() }
    }

    override suspend fun getAnimeDetailsById(id: Int): AnimeDetails? = coroutineScope {
        val detailsJob = async { apiService.getAnimeDetails(id) }
        val recommendationsJob = async { apiService.getAnimeRecommendations(id) }
        val statisticsJob = async { getAnimeStatisticsById(id) }

        val detailsResponse = detailsJob.await().data
        val recommendationsResponse = recommendationsJob.await().data
        val statisticsData = statisticsJob.await()

        return@coroutineScope AnimeDetails(
            id = detailsResponse.malId,
            imageUrl = detailsResponse.images.webp.largeImageUrl,
            title = detailsResponse.title,
            genres = detailsResponse.genres.map { it.toAnimeGenre() },
            description = detailsResponse.synopsis ?: "Описание отсутствует.",
            rating = detailsResponse.score ?: 0f,
            year = detailsResponse.year ?: 2024,
            episodes = detailsResponse.episodes ?: 0,
            ratings = statisticsData,
            recommendedAnime = recommendationsResponse.take(10).map { it.entry.toAnimeCard() }
        )
    }

    override suspend fun searchAnime(query: String, page: Int, limit: Int): List<AnimeCard> {
        if (query.isBlank()) return emptyList()
        val response = apiService.searchAnime(query, page = page, limit = limit)
        return response.data.map { it.toAnimeCard() }
    }

    override suspend fun getAnimeCardById(id: Int): AnimeCard? {
        return try {
            val response = apiService.getAnimeDetails(id)
            response.data.toAnimeCard()
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getAllRecommendationsForId(animeId: Int): List<AnimeCard> {
        val response = apiService.getAnimeRecommendations(id = animeId)
        return response.data.map { recommendationDto ->
            recommendationDto.entry.toAnimeCard()
        }
    }

    override suspend fun getAnimeStatisticsById(id: Int): List<RatingData> {
        val response = apiService.getAnimeStatistics(id)
        return response.data.scores.map { scoreDto ->
            RatingData(
                rating = scoreDto.score,
                userCount = scoreDto.votes
            )
        }
    }
}