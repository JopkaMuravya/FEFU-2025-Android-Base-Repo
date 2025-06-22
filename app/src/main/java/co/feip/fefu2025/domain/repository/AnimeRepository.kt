package co.feip.fefu2025.domain.repository

import co.feip.fefu2025.domain.models.AnimeCard
import co.feip.fefu2025.domain.models.AnimeDetails

interface AnimeRepository {
    suspend fun getAnimeCards(): List<AnimeCard>
    suspend fun getAnimeDetailsById(id: Int): AnimeDetails?
}