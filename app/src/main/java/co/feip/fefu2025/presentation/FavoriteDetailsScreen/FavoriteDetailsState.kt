package co.feip.fefu2025.presentation.FavoriteDetailsScreen

import co.feip.fefu2025.data.local.FavoriteAnimeEntity

data class FavoriteDetailsState(
    val favoriteDetails: FavoriteAnimeEntity? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)