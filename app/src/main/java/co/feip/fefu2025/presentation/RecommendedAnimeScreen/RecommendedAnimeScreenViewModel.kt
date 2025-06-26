package co.feip.fefu2025.presentation.RecommendedAnimeScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.domain.use_cases.GetAllRecommendationsUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RecommendedAnimeScreenViewModel(
    private val animeId: Int,
    private val getAllRecommendationsUseCase: GetAllRecommendationsUseCase = GetAllRecommendationsUseCase()
) : ViewModel() {
    var state by mutableStateOf(RecommendedAnimeScreenState())
        private set

    companion object {
        private const val PAGE_SIZE = 20
    }

    init {
        loadInitialList()
    }

    fun onRetry() {
        loadInitialList()
    }

    private fun loadInitialList() {
        viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)
            try {
                val fullList = getAllRecommendationsUseCase(animeId = animeId)
                state = state.copy(
                    isLoading = false,
                    fullAnimeList = fullList,
                    visibleAnimeList = fullList.take(PAGE_SIZE),
                    endReached = fullList.size <= PAGE_SIZE
                )
            } catch (e: Exception) {
                state = state.copy(isLoading = false, error = e.message ?: "Неизвестная ошибка")
            }
        }
    }

    fun loadNextPage() {
        if (state.isLoadingNextPage || state.endReached) return

        viewModelScope.launch {
            state = state.copy(isLoadingNextPage = true)
            delay(500)

            val currentList = state.visibleAnimeList
            val fullList = state.fullAnimeList

            val nextItems = fullList.subList(
                fromIndex = currentList.size,
                toIndex = (currentList.size + PAGE_SIZE).coerceAtMost(fullList.size)
            )

            state = state.copy(
                visibleAnimeList = currentList + nextItems,
                isLoadingNextPage = false,
                endReached = (currentList.size + nextItems.size) == fullList.size
            )
        }
    }
}