package co.feip.fefu2025.domain.use_cases

import co.feip.fefu2025.data.repository.AnimeRepositoryImpl
import co.feip.fefu2025.domain.models.AnimeCard

class GetAnimeUseCase{
    val repositoryIml = AnimeRepositoryImpl()
    suspend operator fun invoke(
        page: Int,
        limit: Int
    ): List<AnimeCard> {
        return repositoryIml.getAnimeCards(page = page, limit = limit)
    }
}
