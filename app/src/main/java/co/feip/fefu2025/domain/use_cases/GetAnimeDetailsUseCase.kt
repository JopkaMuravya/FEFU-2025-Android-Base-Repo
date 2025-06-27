package co.feip.fefu2025.domain.use_cases

import co.feip.fefu2025.domain.models.AnimeDetails
import co.feip.fefu2025.domain.repository.AnimeRepository

class GetAnimeDetailsUseCase(private val repository: AnimeRepository) {
    suspend operator fun invoke(id: Int): AnimeDetails? {
        return repository.getAnimeDetailsById(id)
    }
}