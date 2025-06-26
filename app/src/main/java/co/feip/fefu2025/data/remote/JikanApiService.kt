package co.feip.fefu2025.data.remote

import co.feip.fefu2025.data.remote.dto.JikanAnimeDto
import co.feip.fefu2025.data.remote.dto.JikanRecommendationDto
import co.feip.fefu2025.data.remote.dto.JikanResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JikanApiService {

    @GET("top/anime")
    suspend fun getTopAnime(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): JikanResponse<List<JikanAnimeDto>>

    @GET("anime")
    suspend fun searchAnime(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): JikanResponse<List<JikanAnimeDto>>

    @GET("anime/{id}/full")
    suspend fun getAnimeDetails(@Path("id") id: Int): JikanResponse<JikanAnimeDto>

    @GET("anime/{id}/recommendations")
    suspend fun getAnimeRecommendations(@Path("id") id: Int): JikanResponse<List<JikanRecommendationDto>>
}