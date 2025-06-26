package co.feip.fefu2025.presentation.RecommendedAnimeScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import co.feip.fefu2025.presentation.common.AnimeCard
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.LaunchedEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendedAnimeScreen(
    state: RecommendedAnimeScreenState,
    navController: NavController,
    navigateToDetails: (Int) -> Unit,
    onPaginate: () -> Unit,
    onRetry: () -> Unit
) {

    val gridState = rememberLazyGridState()

    LaunchedEffect(gridState.layoutInfo, state.visibleAnimeList) {
        val visibleItemsInfo = gridState.layoutInfo.visibleItemsInfo
        if (visibleItemsInfo.isNotEmpty()) {
            val lastVisibleItemIndex = visibleItemsInfo.last().index
            val totalItemCount = gridState.layoutInfo.totalItemsCount

            if (lastVisibleItemIndex >= totalItemCount - 5 && !state.isLoadingNextPage && !state.endReached) {
                onPaginate()
            }
        }
    }

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
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(padding)) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                state.error != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = state.error,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(16.dp)
                        )
                        Button(onClick = onRetry) {
                            Text("Повторить")
                        }
                    }
                }

                state.visibleAnimeList.isEmpty() -> {
                    Text(
                        text = "Нет рекомендуемых аниме",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        state = gridState,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(state.visibleAnimeList) { anime ->
                            AnimeCard(
                                data = anime,
                                modifier = Modifier.padding(8.dp),
                                navigateToDetails = navigateToDetails
                            )
                        }

                        if (state.isLoadingNextPage) {
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                Box(
                                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}