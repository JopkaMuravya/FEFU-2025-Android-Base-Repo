package co.feip.fefu2025.presentation.AnimeDetailScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AnimeDetailsViewModelFactory(
    val id: Int
):  ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AnimeDetailScreenViewModel(id) as T
    }
}