package co.feip.fefu2025

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

import co.feip.fefu2025.RatingData
import co.feip.fefu2025.RatingChart
import co.feip.fefu2025.AnimeCardData
import co.feip.fefu2025.AnimeCard

data class AnimeDetails(
    val imageRes: Int,
    val title: String,
    val genres: List<AnimeGenre>,
    val description: String,
    val rating: Float,
    val year: Int,
    val episodes: Int,
    val ratings: List<RatingData>,
    val recommendedAnime: List<AnimeCardData>
)

@Composable
fun AnimeDetailScreen(
    anime: AnimeDetails,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = anime.imageRes),
            contentDescription = anime.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = anime.title,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            RatingInfo(rating = anime.rating)
            Spacer(modifier = Modifier.width(16.dp))
            YearInfo(year = anime.year)
            Spacer(modifier = Modifier.width(16.dp))
            EpisodesInfo(episodes = anime.episodes)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Жанры:",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        FlexBoxGenreLayout(
            genres = anime.genres,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Описание:",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = anime.description,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        RatingChart(
            ratings = anime.ratings,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Может понравиться",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 12.dp)
        )


        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(anime.recommendedAnime) { animeItem ->
                AnimeCard(
                    data = animeItem,
                    modifier = Modifier.width(200.dp)
                )
            }
        }
    }
}

@Composable
fun FlexBoxGenreLayout(
    genres: List<AnimeGenre>,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current
) {
    AndroidView(
        factory = { ctx ->
            MyFlexBoxLayout(ctx).apply {
                genres.forEach { genre ->
                    addView(createGenreTextView(genre, ctx))
                }
            }
        },
        modifier = modifier
    )
}

private fun createGenreTextView(genre: AnimeGenre, context: Context): TextView {
    return TextView(context).apply {
        text = genre.name
        setTextColor(genre.color.toArgb())
        textSize = 14f
        setPadding(32, 16, 32, 16)

        background = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 24f
            setColor(adjustAlpha(genre.color, 0.1f).toArgb())
            setStroke(2, genre.color.toArgb())
        }

        gravity = Gravity.CENTER
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
}

private fun adjustAlpha(color: Color, factor: Float): Color {
    return color.copy(alpha = color.alpha * factor)
}

@Composable
private fun RatingInfo(rating: Float) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = "Рейтинг",
            tint = Color(0xFFFFC107),
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "%.1f".format(rating),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun YearInfo(year: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = year.toString(),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "год",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun EpisodesInfo(episodes: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = episodes.toString(),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = if (episodes == 1) "эпизод" else "эпизодов",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AnimeDetailScreenPreview() {
    MaterialTheme {
        Surface {
            AnimeDetailScreen(
                anime = AnimeDetails(
                    imageRes = R.drawable.sololeveling1,
                    title = "Поднятие уровня в одиночку",
                    genres = listOf(
                        AnimeGenre("Иссекай", Color(0xFFE91E63)),
                        AnimeGenre("Сёнен", Color(0xFF3F51B5)),
                        AnimeGenre("Фэнтези", Color(0xFF4CAF50)),
                        AnimeGenre("Приключения", Color(0xFFFF9800)),
                        AnimeGenre("Экшен", Color(0xFFF44336))
                    ),
                    description = "Сон Джин-У - самый слабый охотник в мире. " +
                            "Он сражается с монстрами, рискуя жизнью, но после " +
                            "загадочных событий получает уникальную способность..." +
                            "Сон Джин-У - самый слабый охотник в мире. " +
                            "Он сражается с монстрами, рискуя жизнью, но после " +
                            "загадочных событий получает уникальную способность...",
                    rating = 9.8f,
                    year = 2024,
                    episodes = 12,
                    ratings = listOf(
                        RatingData(1, 100),
                        RatingData(2, 50),
                        RatingData(3, 200),
                        RatingData(4, 150),
                        RatingData(5, 300),
                        RatingData(6, 250),
                        RatingData(7, 400),
                        RatingData(8, 350),
                        RatingData(9, 450),
                        RatingData(10, 500)
                    ),
                    recommendedAnime = listOf(
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
                )
            )
        }
    }
}