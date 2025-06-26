package co.feip.fefu2025.presentation.FavoriteDetailsScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.MyApplication
import co.feip.fefu2025.domain.use_cases.favorites.GetFavoriteDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoriteDetailsViewModel(
    application: Application,
    animeId: Int
) : AndroidViewModel(application) {
    private val repository = (application as MyApplication).repository
    private val getFavoriteDetailsUseCase = GetFavoriteDetailsUseCase(repository)

    private val _state = MutableStateFlow(FavoriteDetailsState())
    val state: StateFlow<FavoriteDetailsState> = _state

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
}