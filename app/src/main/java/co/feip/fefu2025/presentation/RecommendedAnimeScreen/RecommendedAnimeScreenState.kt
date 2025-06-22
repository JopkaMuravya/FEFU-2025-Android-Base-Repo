package co.feip.fefu2025.presentation.RecommendedAnimeScreen


import co.feip.fefu2025.domain.models.AnimeCard

data class RecommendedAnimeScreenState(
    val animeList: List<AnimeCard> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)