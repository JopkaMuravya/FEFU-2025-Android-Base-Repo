package co.feip.fefu2025.data.repository

import co.feip.fefu2025.R
import co.feip.fefu2025.domain.models.AnimeCard
import co.feip.fefu2025.domain.models.AnimeDetails
import co.feip.fefu2025.domain.models.AnimeGenre
import co.feip.fefu2025.domain.models.RatingData
import co.feip.fefu2025.domain.repository.AnimeRepository

class AnimeRepositoryImpl : AnimeRepository {
    val anime = AnimeCard(
        imageRes = R.drawable.sololeveling1,
        title = "Поднятие уровня в одиночку",
        genres = listOf(
            AnimeGenre("Иссекай", 0xFFE91E63),
            AnimeGenre("Сёнен", 0xFF3F51B5),
            AnimeGenre("Фэнтези", 0xFF4CAF50)
        ),
        rating = 9.8f,
        episodes = 12,
        id = 1
    )
    val animeList = List(10) { id -> anime.copy(id = id) }
    override suspend fun getAnimeCards(): List<AnimeCard> {
        return animeList
    }


    val data = AnimeDetails(
        imageRes = R.drawable.sololeveling1,
        title = "Поднятие уровня в одиночку",
        genres = listOf(
            AnimeGenre("Иссекай", 0xFFE91E63),
            AnimeGenre("Сёнен", (0xFF3F51B5)),
            AnimeGenre("Фэнтези", (0xFF4CAF50)),
            AnimeGenre("Приключения", (0xFFFF9800)),
            AnimeGenre("Экшен", (0xFFF44336))
        ),
        description = "Сон Джин-У - самый слабый охотник в мире. " +
                "Он сражается с монстрами, рискуя жизнью, но после " +
                "загадочных событий получает уникальную способность..." +
                "Сон Джин-У - самый слабый охотник в мире. " +
                "Он сражается с монстрами, рискуя жизнью, но после " +
                "загадочных событий получает уникальную способность...",
        rating = 9.8f,
        year = 2024,
        episodes = 12,
        ratings = listOf(
            RatingData(1, 100),
            RatingData(2, 50),
            RatingData(3, 200),
            RatingData(4, 150),
            RatingData(5, 300),
            RatingData(6, 250),
            RatingData(7, 400),
            RatingData(8, 350),
            RatingData(9, 450),
            RatingData(10, 500)
        ),
        recommendedAnime = animeList,
        id = 1
    )
    val listOfAnimeData = List(10){id -> data.copy(id=id)}
    override suspend fun getAnimeDetailsById(id: Int): AnimeDetails? {
        return listOfAnimeData.find{ data->
            data.id == id
        }
    }
}