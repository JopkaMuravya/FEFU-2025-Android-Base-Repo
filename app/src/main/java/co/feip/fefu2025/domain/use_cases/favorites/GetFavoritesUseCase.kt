package co.feip.fefu2025.domain.use_cases.favorites

import co.feip.fefu2025.data.local.FavoriteAnimeEntity
import co.feip.fefu2025.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow

class GetFavoritesUseCase(private val repository: AnimeRepository) {
    operator fun invoke(): Flow<List<FavoriteAnimeEntity>> {
        return repository.getFavorites()
    }
}