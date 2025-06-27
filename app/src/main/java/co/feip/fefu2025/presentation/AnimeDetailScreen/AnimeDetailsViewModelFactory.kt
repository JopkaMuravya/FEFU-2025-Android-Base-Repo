package co.feip.fefu2025.presentation.AnimeDetailScreen

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AnimeDetailsViewModelFactory(
    private val application: Application,
    private val id: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnimeDetailScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AnimeDetailScreenViewModel(application, id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}