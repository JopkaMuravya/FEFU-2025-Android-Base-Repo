package co.feip.fefu2025.domain.use_cases

import co.feip.fefu2025.data.repository.AnimeRepositoryImpl
import co.feip.fefu2025.domain.models.AnimeDetails

class GetAnimeDetailsUseCase {
    val repositoryIml = AnimeRepositoryImpl()
    suspend operator fun invoke(id: Int): AnimeDetails? {
        return repositoryIml.getAnimeDetailsById(id)
    }
}