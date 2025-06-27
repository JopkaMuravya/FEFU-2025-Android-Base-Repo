package co.feip.fefu2025.presentation.FavoritesScreen

import co.feip.fefu2025.domain.models.AnimeCard

data class FavoritesScreenState(
    val favoritesList: List<AnimeCard> = emptyList(),
    val isLoading: Boolean = true
)