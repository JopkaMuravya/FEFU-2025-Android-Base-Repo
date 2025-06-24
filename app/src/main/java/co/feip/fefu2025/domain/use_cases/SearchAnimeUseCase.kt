package co.feip.fefu2025.domain.use_cases

import co.feip.fefu2025.data.repository.AnimeRepositoryImpl
import co.feip.fefu2025.domain.models.AnimeCard

class SearchAnimeUseCase {
    private val repository = AnimeRepositoryImpl()
    suspend operator fun invoke(query: String): List<AnimeCard> {
        return repository.searchAnime(query)
    }
}