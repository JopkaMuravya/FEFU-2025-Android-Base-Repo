package co.feip.fefu2025.domain.use_cases

import co.feip.fefu2025.domain.models.AnimeCard
import co.feip.fefu2025.domain.repository.AnimeRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class GetRecommendedAnimeUseCase(private val repository: AnimeRepository) {
    suspend operator fun invoke(ids: List<Int>): List<AnimeCard> = coroutineScope {
        val deferredAnime = ids.map { id ->
            async { repository.getAnimeCardById(id) }
        }
        deferredAnime.mapNotNull { it.await() }
    }
}