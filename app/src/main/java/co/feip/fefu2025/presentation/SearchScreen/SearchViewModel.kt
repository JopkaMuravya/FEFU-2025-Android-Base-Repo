package co.feip.fefu2025.presentation.SearchScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.domain.use_cases.SearchAnimeUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val searchUseCase = SearchAnimeUseCase()
    var state by mutableStateOf(SearchState())
        private set

    private var searchJob: Job? = null

    fun searchAnime(query: String) {
        searchJob?.cancel()

        if (query.isBlank()) {
            state = state.copy(
                searchResults = emptyList(),
                isLoading = false,
                error = null
            )
            return
        }

        state = state.copy(isLoading = true, error = null)

        searchJob = viewModelScope.launch {
            // Добавляем задержку для дебаунса
            delay(500)

            try {
                val results = searchUseCase(query)
                state = state.copy(
                    searchResults = results,
                    isLoading = false
                )
            } catch (e: Exception) {
                state = state.copy(
                    isLoading = false,
                    error = e.message ?: "Ошибка поиска"
                )
            }
        }
    }

    fun clearSearch() {
        state = SearchState()
    }
}