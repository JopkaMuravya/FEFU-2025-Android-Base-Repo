package co.feip.fefu2025.presentation.SearchScreen

import co.feip.fefu2025.domain.models.AnimeCard

data class SearchState(
    val searchResults: List<AnimeCard> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,

    val isLoadingNextPage: Boolean = false,
    val paginationError: String? = null,
    val currentPage: Int = 1,
    val endReached: Boolean = false,

    val currentQuery: String = ""
)