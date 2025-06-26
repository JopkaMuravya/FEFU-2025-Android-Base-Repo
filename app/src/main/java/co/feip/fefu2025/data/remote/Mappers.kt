package co.feip.fefu2025.data.remote

import co.feip.fefu2025.data.remote.dto.JikanAnimeDto
import co.feip.fefu2025.data.remote.dto.JikanGenreDto
import co.feip.fefu2025.domain.models.AnimeCard
import co.feip.fefu2025.domain.models.AnimeGenre

private const val DEFAULT_GENRE_COLOR = 0xFF6C757D

fun JikanAnimeDto.toAnimeCard(): AnimeCard {
    return AnimeCard(
        id = this.malId,
        imageUrl = this.images.webp.largeImageUrl,
        title = this.title,
        genres = this.genres.map { it.toAnimeGenre() },
        rating = this.score ?: 0f,
        episodes = this.episodes
    )
}

fun JikanGenreDto.toAnimeGenre(): AnimeGenre {
    return AnimeGenre(
        name = this.name,
        color = DEFAULT_GENRE_COLOR
    )
}