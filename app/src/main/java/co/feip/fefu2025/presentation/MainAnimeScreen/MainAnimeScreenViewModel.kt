package co.feip.fefu2025.presentation.MainAnimeScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.domain.use_cases.GetAnimeUseCase
import kotlinx.coroutines.launch

class MainAnimeScreenViewModel: ViewModel() {
    val getAnimeUseCase = GetAnimeUseCase()
    var state by mutableStateOf(MainAnimeScreenState())
        private set
    init {
        getAnime()
    }
    private fun getAnime(){
        viewModelScope.launch {
            val anime = getAnimeUseCase()
            state = state.copy(animeList = anime)
        }
    }

    fun onQueryChange(query: String){
        state = state.copy(
            searchQuery = query
        )
    }

}