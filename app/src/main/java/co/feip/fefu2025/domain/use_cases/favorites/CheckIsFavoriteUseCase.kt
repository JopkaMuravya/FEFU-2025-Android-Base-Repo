package co.feip.fefu2025.domain.use_cases.favorites

import co.feip.fefu2025.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow

class CheckIsFavoriteUseCase(private val repository: AnimeRepository) {
    operator fun invoke(animeId: Int): Flow<Boolean> {
        return repository.isFavorite(animeId)
    }
}