package co.feip.fefu2025.presentation.FavoritesScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import co.feip.fefu2025.presentation.common.AnimeCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    state: FavoritesScreenState,
    navigateToMain: () -> Unit,
    navigateToDetails: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Избранное") })
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = false,
                    onClick = navigateToMain,
                    icon = { Icon(Icons.Default.Home, contentDescription = "Главная") },
                    label = { Text("Главная") }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = { /* Уже здесь */ },
                    icon = { Icon(Icons.Default.Star, contentDescription = "Избранное") },
                    label = { Text("Избранное") }
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (state.favoritesList.isEmpty()) {
                Text(
                    text = "Здесь пока ничего нет.\nДобавьте аниме в избранное на экране деталей.",
                    modifier = Modifier.align(Alignment.Center).padding(16.dp),
                    textAlign = TextAlign.Center
                )
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(state.favoritesList) { anime ->
                        AnimeCard(data = anime, modifier = Modifier.padding(8.dp), navigateToDetails = navigateToDetails)
                    }
                }
            }
        }
    }
}