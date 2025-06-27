package co.feip.fefu2025.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteAnimeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(anime: FavoriteAnimeEntity)

    @Query("DELETE FROM favorite_anime WHERE id = :animeId")
    suspend fun removeFavorite(animeId: Int)

    @Query("SELECT * FROM favorite_anime ORDER BY title ASC")
    fun getAllFavorites(): Flow<List<FavoriteAnimeEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_anime WHERE id = :animeId LIMIT 1)")
    fun isFavorite(animeId: Int): Flow<Boolean>

    @Query("SELECT * FROM favorite_anime WHERE id = :animeId")
    suspend fun getFavoriteById(animeId: Int): FavoriteAnimeEntity?
}