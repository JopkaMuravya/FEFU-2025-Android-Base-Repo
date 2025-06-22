package co.feip.fefu2025.domain.models

data class AnimeCard(
    val id: Int,
    val imageRes: Int,
    val title: String,
    val genres: List<AnimeGenre>,
    val rating: Float,
    val episodes: Int? = null
)
