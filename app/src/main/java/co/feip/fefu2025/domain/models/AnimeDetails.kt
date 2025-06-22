package co.feip.fefu2025.domain.models

data class AnimeDetails(
    val id: Int,
    val imageRes: Int,
    val title: String,
    val genres: List<AnimeGenre>,
    val description: String,
    val rating: Float,
    val year: Int,
    val episodes: Int,
    val ratings: List<RatingData>,
    val recommendedAnime: List<AnimeCard>
)