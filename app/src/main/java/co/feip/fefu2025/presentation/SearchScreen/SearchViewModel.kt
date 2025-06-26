package co.feip.fefu2025.presentation.SearchScreen

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.MyApplication
import co.feip.fefu2025.domain.repository.AnimeRepository
import co.feip.fefu2025.domain.use_cases.SearchAnimeUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AnimeRepository = (application as MyApplication).repository
    private val searchUseCase = SearchAnimeUseCase(repository)
    var state by mutableStateOf(SearchState())
        private set

    private var searchJob: Job? = null

    companion object {
        private const val PAGE_SIZE = 20
    }

    fun onQueryChanged(query: String) {
        if (state.currentQuery == query && query.isNotBlank()) return

        searchJob?.cancel()
        state = state.copy(currentQuery = query)

        if (query.isBlank()) {
            state = SearchState(currentQuery = "")
            return
        }

        searchJob = viewModelScope.launch {
            delay(500)
            state = SearchState(currentQuery = query)
            loadNextPage()
        }
    }

    fun loadNextPage() {
        if (state.isLoading || state.isLoadingNextPage || state.endReached) {
            return
        }
        viewModelScope.launch {
            state = if (state.currentPage == 1) {
                state.copy(isLoading = true, error = null)
            } else {
                state.copy(isLoadingNextPage = true, paginationError = null)
            }
            try {
                val results = searchUseCase(
                    query = state.currentQuery,
                    page = state.currentPage,
                    limit = PAGE_SIZE
                )
                state = state.copy(
                    searchResults = state.searchResults + results,
                    currentPage = state.currentPage + 1,
                    isLoading = false,
                    isLoadingNextPage = false,
                    endReached = results.isEmpty()
                )
            } catch (e: Exception) {
                val errorMessage = e.message ?: "Ошибка поиска"
                if (state.currentPage == 1) {
                    state = state.copy(isLoading = false, error = errorMessage)
                } else {
                    state = state.copy(isLoadingNextPage = false, paginationError = errorMessage)
                }
            }
        }
    }
}