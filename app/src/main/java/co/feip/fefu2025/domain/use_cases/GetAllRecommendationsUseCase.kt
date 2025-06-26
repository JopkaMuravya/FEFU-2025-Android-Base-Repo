package co.feip.fefu2025.domain.use_cases

import co.feip.fefu2025.data.repository.AnimeRepositoryImpl
import co.feip.fefu2025.domain.models.AnimeCard

class GetAllRecommendationsUseCase {
    private val repository = AnimeRepositoryImpl()

    suspend operator fun invoke(animeId: Int): List<AnimeCard> {
        return repository.getAllRecommendationsForId(animeId)
    }
}