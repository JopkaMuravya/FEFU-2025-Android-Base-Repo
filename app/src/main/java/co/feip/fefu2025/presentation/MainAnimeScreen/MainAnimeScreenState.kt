package co.feip.fefu2025.presentation.MainAnimeScreen

import co.feip.fefu2025.domain.models.AnimeCard

data class MainAnimeScreenState(
    val animeList:List<AnimeCard> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = ""
)
