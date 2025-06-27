package co.feip.fefu2025.domain.use_cases

import co.feip.fefu2025.domain.models.AnimeCard
import co.feip.fefu2025.domain.repository.AnimeRepository

class GetAnimeUseCase(private val repository: AnimeRepository) {
    suspend operator fun invoke(page: Int, limit: Int): List<AnimeCard> {
        return repository.getAnimeCards(page = page, limit = limit)
    }
}
