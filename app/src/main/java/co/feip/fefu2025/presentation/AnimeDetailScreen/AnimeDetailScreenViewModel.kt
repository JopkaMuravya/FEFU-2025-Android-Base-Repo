package co.feip.fefu2025.presentation.AnimeDetailScreen

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.MyApplication
import co.feip.fefu2025.data.local.FavoriteAnimeEntity
import co.feip.fefu2025.domain.repository.AnimeRepository
import co.feip.fefu2025.domain.use_cases.GetAnimeDetailsUseCase
import co.feip.fefu2025.domain.use_cases.favorites.AddFavoriteUseCase
import co.feip.fefu2025.domain.use_cases.favorites.CheckIsFavoriteUseCase
import co.feip.fefu2025.domain.use_cases.favorites.RemoveFavoriteUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AnimeDetailScreenViewModel(
    application: Application,
    val id: Int
) : AndroidViewModel(application) {

    private val repository: AnimeRepository = (application as MyApplication).repository
    private val getAnimeDetailsUseCase = GetAnimeDetailsUseCase(repository)
    private val checkIsFavoriteUseCase = CheckIsFavoriteUseCase(repository)
    private val addFavoriteUseCase = AddFavoriteUseCase(repository)
    private val removeFavoriteUseCase = RemoveFavoriteUseCase(repository)

    var state by mutableStateOf(AnimeDetailsState())
        private set

    val isFavorite = checkIsFavoriteUseCase(id)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    init {
        loadAnimeDetails()
    }

    fun loadAnimeDetails() {
        state = state.copy(isLoading = true, error = null)
        viewModelScope.launch {
            try {
                val details = getAnimeDetailsUseCase(id)
                state = state.copy(details = details, isLoading = false)
            } catch (e: Exception) {
                state = state.copy(isLoading = false, error = e.message ?: "Неизвестная ошибка")
            }
        }
    }

    fun onFavoriteClick() {
        viewModelScope.launch {
            val details = state.details ?: return@launch
            if (isFavorite.value) {
                removeFavoriteUseCase(details.id)
            } else {
                val favoriteEntity = FavoriteAnimeEntity(
                    id = details.id,
                    title = details.title,
                    description = details.description,
                    imageUrl = details.imageUrl
                )
                addFavoriteUseCase(favoriteEntity)
            }
        }
    }
}