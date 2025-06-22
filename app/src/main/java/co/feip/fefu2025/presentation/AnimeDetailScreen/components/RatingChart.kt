package co.feip.fefu2025.presentation.AnimeDetailScreen.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class RatingData(
    val rating: Int,
    val userCount: Int
)

@Composable
fun RatingChart(
    ratings: List<co.feip.fefu2025.domain.models.RatingData>,
    modifier: Modifier = Modifier
) {
    val maxUserCount = ratings.maxOf { it.userCount }
    val totalItems = ratings.size
    val cornerRadius = 10f

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Рейтинг аниме", fontSize = 20.sp, color = Color(0xFF333333))

        Spacer(modifier = Modifier.height(16.dp))

        BoxWithConstraints(
            modifier = Modifier.fillMaxWidth()
        ) {
            val availableWidth = maxWidth - 32.dp
            val barWidth = (availableWidth / totalItems * 0.7f).coerceAtMost(40.dp)
            val spacing = (availableWidth / totalItems * 0.3f).coerceAtLeast(8.dp)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ratings.forEach { ratingData ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.width(barWidth + spacing)
                    ) {
                        // Количество голосов над столбцом
                        Text(
                            text = ratingData.userCount.toString(),
                            fontSize = 12.sp,
                            color = Color(0xFF666666),
                            modifier = Modifier.padding(bottom = 4.dp)
                        )

                        Canvas(
                            modifier = Modifier
                                .width(barWidth)
                                .height(200.dp)
                        ) {
                            val barHeight = (ratingData.userCount.toFloat() / maxUserCount * size.height)

                            // Цвет от красного (1 звезда) до зелёного (10 звёзд)
                            val color = when (ratingData.rating) {
                                1 -> Color(0xFFFF5252)  // Красный
                                2 -> Color(0xFFFF7043)
                                3 -> Color(0xFFFFAB40)
                                4 -> Color(0xFFFFD740)
                                5 -> Color(0xFFFFFF8D)
                                6 -> Color(0xFFC6FF00)
                                7 -> Color(0xFFAEEA00)
                                8 -> Color(0xFF64DD17)
                                9 -> Color(0xFF00C853)
                                10 -> Color(0xFF00BFA5)  // Бирюзово-зелёный
                                else -> Color(0xFF4CAF50)
                            }

                            // Основной столбец
                            drawRoundRect(
                                color = color,
                                topLeft = androidx.compose.ui.geometry.Offset(0f, size.height - barHeight),
                                size = androidx.compose.ui.geometry.Size(size.width, barHeight),
                                cornerRadius = androidx.compose.ui.geometry.CornerRadius(cornerRadius, cornerRadius)
                            )

                            // Обводка столбца
                            drawRoundRect(
                                color = color.copy(alpha = 0.8f),
                                topLeft = androidx.compose.ui.geometry.Offset(0f, size.height - barHeight),
                                size = androidx.compose.ui.geometry.Size(size.width, barHeight),
                                cornerRadius = androidx.compose.ui.geometry.CornerRadius(cornerRadius, cornerRadius),
                                style = Stroke(width = 1.5.dp.toPx())
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        // Оценка под столбцом
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(24.dp)
                                .background(
                                    color = Color(0xFFF5F5F5),
                                    shape = androidx.compose.foundation.shape.CircleShape
                                )
                        ) {
                            Text(
                                text = ratingData.rating.toString(),
                                fontSize = 12.sp,
                                color = Color(0xFF333333),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
}

//@Preview(showBackground = true, widthDp = 400)
//@Composable
//fun PreviewRatingChart() {
//    val sampleData = listOf(
//        RatingData(1, 100),
//        RatingData(2, 50),
//        RatingData(3, 200),
//        RatingData(4, 150),
//        RatingData(5, 300),
//        RatingData(6, 250),
//        RatingData(7, 400),
//        RatingData(8, 350),
//        RatingData(9, 450),
//        RatingData(10, 500)
//    )
//
//    RatingChart(ratings = sampleData)
//}