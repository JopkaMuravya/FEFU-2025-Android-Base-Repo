package co.feip.fefu2025

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.systemBars

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAnimeScreen(
    animeList: List<AnimeCardData>,
    modifier: Modifier = Modifier
) {
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        topBar = {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 4.dp,
                tonalElevation = 2.dp,
                modifier = Modifier.padding(top = systemBarsPadding.calculateTopPadding())
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "AniJopka",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 16.dp)
                    ) {
                        TextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Поиск аниме...") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                                disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            textStyle = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        },
        modifier = modifier
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = innerPadding,
            modifier = Modifier.fillMaxSize()
        ) {
            items(animeList) { anime ->
                AnimeCard(
                    data = anime,
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainAnimeScreenPreview() {
    val sampleAnimeList = listOf(
        AnimeCardData(imageRes = R.drawable.sololeveling1, title = "Поднятие уровня в одиночку", genres = listOf(AnimeGenre("Иссекай", Color(0xFFE91E63)), AnimeGenre("Сёнен", Color(0xFF3F51B5)), AnimeGenre("Фэнтези", Color(0xFF4CAF50))), rating = 9.8f, episodes = 12),
        AnimeCardData(imageRes = R.drawable.sololeveling1, title = "Поднятие уровня в одиночку", genres = listOf(AnimeGenre("Иссекай", Color(0xFFE91E63)), AnimeGenre("Сёнен", Color(0xFF3F51B5)), AnimeGenre("Фэнтези", Color(0xFF4CAF50))), rating = 9.8f, episodes = 12),
        AnimeCardData(imageRes = R.drawable.sololeveling1, title = "Поднятие уровня в одиночку", genres = listOf(AnimeGenre("Иссекай", Color(0xFFE91E63)), AnimeGenre("Сёнен", Color(0xFF3F51B5)), AnimeGenre("Фэнтези", Color(0xFF4CAF50))), rating = 9.8f, episodes = 12),
        AnimeCardData(imageRes = R.drawable.sololeveling1, title = "Поднятие уровня в одиночку", genres = listOf(AnimeGenre("Иссекай", Color(0xFFE91E63)), AnimeGenre("Сёнен", Color(0xFF3F51B5)), AnimeGenre("Фэнтези", Color(0xFF4CAF50))), rating = 9.8f, episodes = 12),
        AnimeCardData(imageRes = R.drawable.sololeveling1, title = "Поднятие уровня в одиночку", genres = listOf(AnimeGenre("Иссекай", Color(0xFFE91E63)), AnimeGenre("Сёнен", Color(0xFF3F51B5)), AnimeGenre("Фэнтези", Color(0xFF4CAF50))), rating = 9.8f, episodes = 12),
        AnimeCardData(imageRes = R.drawable.sololeveling1, title = "Поднятие уровня в одиночку", genres = listOf(AnimeGenre("Иссекай", Color(0xFFE91E63)), AnimeGenre("Сёнен", Color(0xFF3F51B5)), AnimeGenre("Фэнтези", Color(0xFF4CAF50))), rating = 9.8f, episodes = 12),
        AnimeCardData(imageRes = R.drawable.sololeveling1, title = "Поднятие уровня в одиночку", genres = listOf(AnimeGenre("Иссекай", Color(0xFFE91E63)), AnimeGenre("Сёнен", Color(0xFF3F51B5)), AnimeGenre("Фэнтези", Color(0xFF4CAF50))), rating = 9.8f, episodes = 12),
        AnimeCardData(imageRes = R.drawable.sololeveling1, title = "Поднятие уровня в одиночку", genres = listOf(AnimeGenre("Иссекай", Color(0xFFE91E63)), AnimeGenre("Сёнен", Color(0xFF3F51B5)), AnimeGenre("Фэнтези", Color(0xFF4CAF50))), rating = 9.8f, episodes = 12),
        AnimeCardData(imageRes = R.drawable.sololeveling1, title = "Поднятие уровня в одиночку", genres = listOf(AnimeGenre("Иссекай", Color(0xFFE91E63)), AnimeGenre("Сёнен", Color(0xFF3F51B5)), AnimeGenre("Фэнтези", Color(0xFF4CAF50))), rating = 9.8f, episodes = 12),
        AnimeCardData(imageRes = R.drawable.sololeveling1, title = "Поднятие уровня в одиночку", genres = listOf(AnimeGenre("Иссекай", Color(0xFFE91E63)), AnimeGenre("Сёнен", Color(0xFF3F51B5)), AnimeGenre("Фэнтези", Color(0xFF4CAF50))), rating = 9.8f, episodes = 12),
    )

    MaterialTheme {
        MainAnimeScreen(animeList = sampleAnimeList)
    }
}