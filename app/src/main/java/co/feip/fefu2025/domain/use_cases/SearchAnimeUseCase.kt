package co.feip.fefu2025.domain.use_cases

import co.feip.fefu2025.domain.models.AnimeCard
import co.feip.fefu2025.domain.repository.AnimeRepository

class SearchAnimeUseCase(private val repository: AnimeRepository) {
    suspend operator fun invoke(query: String, page: Int, limit: Int): List<AnimeCard> {
        return repository.searchAnime(query, page, limit)
    }
}