package co.feip.fefu2025.presentation.SearchScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import co.feip.fefu2025.domain.models.AnimeCard
import co.feip.fefu2025.presentation.common.AnimeCard
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    searchResults: List<AnimeCard>,
    isLoading: Boolean,
    error: String?,
    isLoadingNextPage: Boolean,
    paginationError: String?,
    onPaginate: () -> Unit,
    onRetryPagination: () -> Unit,
    onBackClick: () -> Unit,
    navigateToDetails: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    val gridState = rememberLazyGridState()

    LaunchedEffect(gridState.layoutInfo, searchResults) {
        val visibleItemsInfo = gridState.layoutInfo.visibleItemsInfo
        if (visibleItemsInfo.isNotEmpty()) {
            val lastVisibleItemIndex = visibleItemsInfo.last().index
            val totalItemCount = gridState.layoutInfo.totalItemsCount

            if (lastVisibleItemIndex >= totalItemCount - 5 && !isLoadingNextPage && !isLoading) {
                onPaginate()
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    TextField(
                        value = searchQuery,
                        onValueChange = onSearchQueryChange,
                        placeholder = { Text("Поиск аниме...") },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when {
                isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize()
                    )
                }
                error != null -> {
                    Text(
                        text = error,
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize()
                    )
                }
                searchQuery.isBlank() -> {
                    Text(
                        text = "Введите запрос для поиска",
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize()
                    )
                }
                searchResults.isEmpty() -> {
                    Text(
                        text = "Ничего не найдено",
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize()
                    )
                }
                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        state = gridState,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(searchResults) { anime ->
                            AnimeCard(
                                data = anime,
                                modifier = Modifier.padding(8.dp),
                                navigateToDetails = navigateToDetails
                            )
                        }

                        if (isLoadingNextPage) {
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                                    CircularProgressIndicator()
                                }
                            }
                        }

                        if (paginationError != null) {
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                                    Text(text = paginationError, color = MaterialTheme.colorScheme.error)
                                    Button(onClick = onRetryPagination) { Text("Повторить") }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}