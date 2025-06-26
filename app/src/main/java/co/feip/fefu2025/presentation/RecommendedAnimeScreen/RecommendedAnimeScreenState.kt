package co.feip.fefu2025.presentation.RecommendedAnimeScreen


import co.feip.fefu2025.domain.models.AnimeCard

data class RecommendedAnimeScreenState(
    val visibleAnimeList: List<AnimeCard> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,

    val isLoadingNextPage: Boolean = false,
    val endReached: Boolean = false,

    internal val fullAnimeList: List<AnimeCard> = emptyList()
)