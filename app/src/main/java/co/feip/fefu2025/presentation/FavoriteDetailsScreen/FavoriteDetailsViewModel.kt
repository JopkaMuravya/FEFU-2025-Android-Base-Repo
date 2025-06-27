package co.feip.fefu2025.presentation.FavoriteDetailsScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.MyApplication
import co.feip.fefu2025.domain.use_cases.favorites.GetFavoriteDetailsUseCase
import co.feip.fefu2025.domain.use_cases.favorites.RemoveFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class FavoriteDetailsViewModel(
    application: Application,
    private val animeId: Int
) : AndroidViewModel(application) {
    private val repository = (application as MyApplication).repository
    private val getFavoriteDetailsUseCase = GetFavoriteDetailsUseCase(repository)
    private val removeFavoriteUseCase = RemoveFavoriteUseCase(repository)

    private val _state = MutableStateFlow(FavoriteDetailsState())
    val state: StateFlow<FavoriteDetailsState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getFavoriteDetails(animeId)
    }

    private fun getFavoriteDetails(animeId: Int) {
        viewModelScope.launch {
            val details = getFavoriteDetailsUseCase(animeId)
            if (details != null) {
                _state.value = FavoriteDetailsState(favoriteDetails = details, isLoading = false)
            } else {
                _state.value = FavoriteDetailsState(error = "Не удалось загрузить детали", isLoading = false)
            }
        }
    }

    fun onRemoveFromFavorites() {
        viewModelScope.launch {
            removeFavoriteUseCase(animeId)
            _eventFlow.emit(UiEvent.NavigateBack)
        }
    }

    sealed class UiEvent {
        object NavigateBack : UiEvent()
    }
}