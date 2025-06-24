package co.feip.fefu2025.presentation.SearchScreen

import co.feip.fefu2025.domain.models.AnimeCard

data class SearchState(
    val searchResults: List<AnimeCard> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)