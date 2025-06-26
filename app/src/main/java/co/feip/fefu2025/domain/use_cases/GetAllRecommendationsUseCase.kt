package co.feip.fefu2025.domain.use_cases

import co.feip.fefu2025.domain.models.AnimeCard
import co.feip.fefu2025.domain.repository.AnimeRepository

class GetAllRecommendationsUseCase(private val repository: AnimeRepository) {
    suspend operator fun invoke(animeId: Int): List<AnimeCard> {
        return repository.getAllRecommendationsForId(animeId)
    }
}