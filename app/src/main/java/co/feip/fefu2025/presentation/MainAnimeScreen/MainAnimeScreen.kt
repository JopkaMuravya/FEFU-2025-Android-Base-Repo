package co.feip.fefu2025.presentation.MainAnimeScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import co.feip.fefu2025.presentation.common.AnimeCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAnimeScreen(
    state: MainAnimeScreenState,
    modifier: Modifier = Modifier,
    navigateTODetails: (Int) -> Unit,
    navigateToSearch: () -> Unit,
    navigateToFavorites: () -> Unit,
    onQueryChange: (String) -> Unit,
    onPaginate: () -> Unit,
    onRetry: () -> Unit
) {
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()
    val gridState = rememberLazyGridState()

    LaunchedEffect(gridState.layoutInfo, state.animeList) {
        val visibleItemsInfo = gridState.layoutInfo.visibleItemsInfo
        if (visibleItemsInfo.isNotEmpty()) {
            val lastVisibleItemIndex = visibleItemsInfo.last().index
            if (lastVisibleItemIndex >= gridState.layoutInfo.totalItemsCount - 5 && !state.isLoadingNextPage && !state.endReached) {
                onPaginate()
            }
        }
    }

    Scaffold(
        topBar = {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 4.dp,
                modifier = Modifier.padding(top = systemBarsPadding.calculateTopPadding())
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().height(64.dp).padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "AniJopka",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    )
                    Box(modifier = Modifier.weight(1f).padding(start = 16.dp)) {
                        TextField(
                            value = state.searchQuery,
                            onValueChange = onQueryChange,
                            placeholder = { Text("Поиск аниме...") },
                            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                                disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth().clickable { navigateToSearch() },
                            enabled = false, singleLine = true, textStyle = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = true,
                    onClick = { /* Уже здесь, ничего не делаем */ },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Главная") },
                    label = { Text("Главная") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = navigateToFavorites,
                    icon = { Icon(Icons.Default.Star, contentDescription = "Избранное") },
                    label = { Text("Избранное") }
                )
            }
        },
        modifier = modifier
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            when {
                state.isLoading -> { CircularProgressIndicator(modifier = Modifier.align(Alignment.Center)) }
                state.error != null && state.animeList.isEmpty() -> {
                    Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = state.error, color = MaterialTheme.colorScheme.error)
                        Button(onClick = onRetry) { Text("Повторить") }
                    }
                }
                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        state = gridState,
                        contentPadding = PaddingValues(8.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(state.animeList) { anime ->
                            AnimeCard(data = anime, modifier = Modifier.padding(8.dp), navigateToDetails = navigateTODetails)
                        }
                        if (state.isLoadingNextPage) {
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                Box(modifier = Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
                            }
                        }
                        if (state.paginationError != null) {
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                Column(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                                    Text(text = state.paginationError, color = MaterialTheme.colorScheme.error)
                                    Button(onClick = onRetry) { Text("Повторить") }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}