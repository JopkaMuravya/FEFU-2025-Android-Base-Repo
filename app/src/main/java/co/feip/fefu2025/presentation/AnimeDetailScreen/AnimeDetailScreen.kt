package co.feip.fefu2025.presentation.AnimeDetailScreen

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.style.TextDecoration
import co.feip.fefu2025.R
import co.feip.fefu2025.domain.models.AnimeDetails
import co.feip.fefu2025.domain.models.AnimeGenre
import co.feip.fefu2025.presentation.AnimeDetailScreen.components.RatingChart
import co.feip.fefu2025.presentation.common.AnimeCard
import co.feip.fefu2025.presentation.common.MyFlexBoxLayout
import coil.compose.AsyncImage

@Composable
fun AnimeDetailScreen(
    state: AnimeDetailsState,
    modifier: Modifier = Modifier,
    navigateToDetails: (Int) -> Unit,
    navigateToRecommended: (Int) -> Unit,
    onRetry: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        when {
            state.isLoading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
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
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                    Button(
                        onClick = onRetry,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("Повторить")
                    }
                }
            }

            state.details != null -> {
                AnimeDetailContent(
                    details = state.details,
                    navigateToDetails = navigateToDetails,
                    navigateToRecommended = navigateToRecommended
                )
            }
        }
    }
}

@Composable
private fun AnimeDetailContent(
    details: AnimeDetails,
    navigateToDetails: (Int) -> Unit,
    navigateToRecommended: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        AsyncImage(
            model = details.imageUrl,
            contentDescription = details.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(12.dp)),
            placeholder = painterResource(id = R.drawable.placeholder_image),
            error = painterResource(id = R.drawable.error_image)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = details.title,
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
            RatingInfo(rating = details.rating)
            Spacer(modifier = Modifier.width(16.dp))
            YearInfo(year = details.year)
            Spacer(modifier = Modifier.width(16.dp))
            EpisodesInfo(episodes = details.episodes)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Жанры:",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        FlexBoxGenreLayout(
            genres = details.genres,
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
            text = details.description,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        RatingChart(
            ratings = details.ratings,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Может понравиться",
            style = MaterialTheme.typography.titleLarge.copy(
                textDecoration = TextDecoration.Underline,
                color = MaterialTheme.colorScheme.onSurface
            ),
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(bottom = 12.dp)
                .clickable {
                    navigateToRecommended(details.id)
                }
                .background(
                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    shape = MaterialTheme.shapes.small
                )
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(details.recommendedAnime) { animeItem ->
                AnimeCard(
                    data = animeItem,
                    modifier = Modifier.width(200.dp),
                    navigateToDetails = navigateToDetails
                )
            }
        }
    }
}

@Composable
private fun FlexBoxGenreLayout(
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
        setTextColor(Color(genre.color).toArgb())
        textSize = 14f
        setPadding(32, 16, 32, 16)

        background = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 24f
            setColor(adjustAlpha(Color(genre.color), 0.1f).toArgb())
            setStroke(2, Color(genre.color).toArgb())
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
            modifier = Modifier.size(20.dp))
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