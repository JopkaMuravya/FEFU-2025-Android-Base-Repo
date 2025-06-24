package co.feip.fefu2025.presentation.AnimeDetailScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.domain.use_cases.GetAnimeDetailsUseCase
import kotlinx.coroutines.launch

class AnimeDetailScreenViewModel(
    val id: Int
): ViewModel() {
    private val animeDetailsUseCase = GetAnimeDetailsUseCase()
    var state by mutableStateOf(AnimeDetailsState())

    init {
        loadAnimeDetails()
    }

    fun loadAnimeDetails() {
        state = state.copy(
            isLoading = true,
            error = null
        )

        viewModelScope.launch {
            try {
                val details = animeDetailsUseCase(id)
                state = state.copy(
                    details = details,
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
}