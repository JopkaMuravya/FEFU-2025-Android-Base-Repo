package co.feip.fefu2025.presentation.RecommendedAnimeScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import co.feip.fefu2025.presentation.common.AnimeCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendedAnimeScreen(
    state: RecommendedAnimeScreenState,
    navController: NavController,
    navigateToDetails: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Рекомендуемые аниме") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = padding,
            modifier = Modifier.fillMaxSize()
        ) {
            items(state.animeList) { anime ->
                AnimeCard(
                    data = anime,
                    modifier = Modifier.padding(8.dp),
                    navigateToDetails = navigateToDetails
                )
            }
        }
    }
}