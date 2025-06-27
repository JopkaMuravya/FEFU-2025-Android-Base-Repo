package co.feip.fefu2025.presentation.FavoritesScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.MyApplication
import co.feip.fefu2025.domain.models.AnimeCard
import co.feip.fefu2025.domain.use_cases.favorites.GetFavoritesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = (application as MyApplication).repository
    private val getFavoritesUseCase = GetFavoritesUseCase(repository)

    private val _state = MutableStateFlow(FavoritesScreenState())
    val state: StateFlow<FavoritesScreenState> = _state

    init {
        getFavorites()
    }

    private fun getFavorites() {
        getFavoritesUseCase().onEach { favorites ->
            val animeCards = favorites.map { entity ->
                AnimeCard(
                    id = entity.id,
                    title = entity.title,
                    imageUrl = entity.imageUrl,
                    genres = emptyList(),
                    rating = 0f,
                    episodes = null
                )
            }
            _state.value = FavoritesScreenState(favoritesList = animeCards, isLoading = false)
        }.launchIn(viewModelScope)
    }
}