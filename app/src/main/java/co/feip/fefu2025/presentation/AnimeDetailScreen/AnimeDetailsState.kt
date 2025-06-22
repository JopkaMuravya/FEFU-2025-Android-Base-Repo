package co.feip.fefu2025.presentation.AnimeDetailScreen

import co.feip.fefu2025.domain.models.AnimeDetails

data class AnimeDetailsState(
    val details: AnimeDetails? = null,
    val error: String? = null,
    val isLoading: Boolean = false,
)
