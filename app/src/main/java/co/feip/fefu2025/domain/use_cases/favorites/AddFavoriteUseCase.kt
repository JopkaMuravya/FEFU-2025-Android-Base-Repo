package co.feip.fefu2025.domain.use_cases.favorites

import co.feip.fefu2025.data.local.FavoriteAnimeEntity
import co.feip.fefu2025.domain.repository.AnimeRepository

class AddFavoriteUseCase(private val repository: AnimeRepository) {
    suspend operator fun invoke(anime: FavoriteAnimeEntity) {
        repository.addFavorite(anime)
    }
}