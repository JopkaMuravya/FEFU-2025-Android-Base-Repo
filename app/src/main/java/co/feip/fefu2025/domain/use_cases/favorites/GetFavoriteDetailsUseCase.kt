package co.feip.fefu2025.domain.use_cases.favorites

import co.feip.fefu2025.data.local.FavoriteAnimeEntity
import co.feip.fefu2025.domain.repository.AnimeRepository

class GetFavoriteDetailsUseCase(private val repository: AnimeRepository) {
    suspend operator fun invoke(animeId: Int): FavoriteAnimeEntity? {
        return repository.getFavoriteById(animeId)
    }
}