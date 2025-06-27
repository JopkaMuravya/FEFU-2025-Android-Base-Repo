package co.feip.fefu2025.presentation.MainAnimeScreen

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.MyApplication
import co.feip.fefu2025.domain.repository.AnimeRepository
import co.feip.fefu2025.domain.use_cases.GetAnimeUseCase
import kotlinx.coroutines.launch

class MainAnimeScreenViewModel(application: Application): AndroidViewModel(application) {
    private val repository: AnimeRepository = (application as MyApplication).repository
    private val getAnimeUseCase = GetAnimeUseCase(repository)
    var state by mutableStateOf(MainAnimeScreenState())
        private set

    companion object {
        private const val PAGE_SIZE = 20
    }

    init {
        loadNextItems()
    }

    fun loadNextItems() {
        if (state.isLoadingNextPage || state.endReached || state.isLoading) {
            return
        }
        viewModelScope.launch {
            state = state.copy(paginationError = null)
            state = if (state.currentPage == 1) {
                state.copy(isLoading = true, error = null)
            } else {
                state.copy(isLoadingNextPage = true)
            }
            try {
                val newAnime = getAnimeUseCase(page = state.currentPage, limit = PAGE_SIZE)
                state = state.copy(
                    animeList = state.animeList + newAnime,
                    currentPage = state.currentPage + 1,
                    isLoading = false,
                    isLoadingNextPage = false,
                    endReached = newAnime.isEmpty()
                )
            } catch (e: Exception) {
                val errorMessage = e.message ?: "Неизвестная ошибка"
                if (state.currentPage == 1) {
                    state = state.copy(isLoading = false, error = errorMessage)
                } else {
                    state = state.copy(isLoadingNextPage = false, paginationError = errorMessage)
                }
            }
        }
    }

    fun retry() {
        loadNextItems()
    }

    fun onQueryChange(query: String) {
        state = state.copy(searchQuery = query)
    }
}