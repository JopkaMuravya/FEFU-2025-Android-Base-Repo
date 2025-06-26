package co.feip.fefu2025.domain.use_cases

import co.feip.fefu2025.data.repository.AnimeRepositoryImpl
import co.feip.fefu2025.domain.models.AnimeCard
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class GetRecommendedAnimeUseCase {
    private val repository = AnimeRepositoryImpl()

    suspend operator fun invoke(ids: List<Int>): List<AnimeCard> = coroutineScope {
        val deferredAnime = ids.map { id ->
            async { repository.getAnimeCardById(id) }
        }
        deferredAnime.mapNotNull { it.await() }
    }
}