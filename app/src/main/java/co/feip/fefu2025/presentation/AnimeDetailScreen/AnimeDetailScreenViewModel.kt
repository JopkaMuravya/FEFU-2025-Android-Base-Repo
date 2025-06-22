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
    //TODO передать id
    val animeDetailsUseCase = GetAnimeDetailsUseCase()
    var state by mutableStateOf(AnimeDetailsState())
    init {
        getAnimeDetails()
    }
    private fun getAnimeDetails(){
        viewModelScope.launch {
           val details = animeDetailsUseCase(id)
            state = state.copy(details = details)
        }
    }

}

