package co.feip.fefu2025.presentation.MainAnimeScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.domain.use_cases.GetAnimeUseCase
import kotlinx.coroutines.launch

class MainAnimeScreenViewModel: ViewModel() {
    private val getAnimeUseCase = GetAnimeUseCase()
    var state by mutableStateOf(MainAnimeScreenState())
        private set

    init {
        loadAnime()
    }

    fun loadAnime() {
        state = state.copy(
            isLoading = true,
            error = null
        )

        viewModelScope.launch {
            try {
                val anime = getAnimeUseCase()
                state = state.copy(
                    animeList = anime,
                    isLoading = false
                )
            } catch (e: Exception) {
                state = state.copy(
                    isLoading = false,
                    error = e.message ?: "Неизвестная ошибка"
                )
            }
        }
    }

    fun onQueryChange(query: String) {
        state = state.copy(searchQuery = query)
    }
}