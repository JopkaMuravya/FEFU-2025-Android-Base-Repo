package co.feip.fefu2025.domain.use_cases.favorites

import co.feip.fefu2025.domain.repository.AnimeRepository

class RemoveFavoriteUseCase(private val repository: AnimeRepository) {
    suspend operator fun invoke(animeId: Int) {
        repository.removeFavorite(animeId)
    }
}