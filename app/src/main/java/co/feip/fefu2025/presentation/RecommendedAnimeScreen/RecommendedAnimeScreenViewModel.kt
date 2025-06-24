package co.feip.fefu2025.presentation.RecommendedAnimeScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.domain.use_cases.GetAnimeUseCase
import kotlinx.coroutines.launch

class RecommendedAnimeScreenViewModel(
    private val animeIds: List<Int>,
    private val getAnimeUseCase: GetAnimeUseCase = GetAnimeUseCase()
) : ViewModel() {
    var state by mutableStateOf(RecommendedAnimeScreenState())
        private set

    init {
        loadRecommendedAnime()
    }

    fun loadRecommendedAnime() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null
            )

            try {
                val allAnime = getAnimeUseCase()
                val recommended = allAnime.filter { it.id in animeIds }
                state = state.copy(
                    animeList = recommended,
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