package co.feip.fefu2025

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import co.feip.fefu2025.presentation.AnimeDetailScreen.AnimeDetailScreen
import co.feip.fefu2025.presentation.AnimeDetailScreen.AnimeDetailScreenViewModel
import co.feip.fefu2025.presentation.AnimeDetailScreen.AnimeDetailsViewModelFactory
import co.feip.fefu2025.presentation.MainAnimeScreen.MainAnimeScreen
import co.feip.fefu2025.presentation.MainAnimeScreen.MainAnimeScreenViewModel
import co.feip.fefu2025.presentation.RecommendedAnimeScreen.RecommendedAnimeScreen
import co.feip.fefu2025.presentation.RecommendedAnimeScreen.RecommendedAnimeScreenViewModel
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController: NavHostController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = Destination.MainScreen
            ) {
                composable<Destination.MainScreen> {
                    val viewModel: MainAnimeScreenViewModel = viewModel()
                    MainAnimeScreen(
                        state = viewModel.state,
                        modifier = Modifier,
                        onQueryChange = viewModel::onQueryChange,
                        navigateTODetails = { id -> navController.navigate(Destination.AnimeDetails(id)) },
                    )
                }

                composable<Destination.AnimeDetails> { backStackEntry ->
                    val id = backStackEntry.toRoute<Destination.AnimeDetails>().id
                    val factory = AnimeDetailsViewModelFactory(id)
                    val viewModel: AnimeDetailScreenViewModel = viewModel(factory = factory)
                    AnimeDetailScreen(
                        state = viewModel.state,
                        modifier = Modifier,
                        navigateToDetails = { id -> navController.navigate(Destination.AnimeDetails(id)) },
                        navigateToRecommended = { ids -> navController.navigate(Destination.RecommendedAnime(ids)) }
                    )
                }

                composable<Destination.RecommendedAnime> { backStackEntry ->
                    val args = backStackEntry.toRoute<Destination.RecommendedAnime>()
                    val viewModel: RecommendedAnimeScreenViewModel = viewModel(
                        factory = object : ViewModelProvider.Factory {
                            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                                return RecommendedAnimeScreenViewModel(args.animeIds) as T
                            }
                        }
                    )
                    RecommendedAnimeScreen(
                        state = viewModel.state,
                        navController = navController,
                        navigateToDetails = { id -> navController.navigate(Destination.AnimeDetails(id)) }
                    )
                }
            }
        }
    }
}

sealed class Destination {
    @Serializable
    data class AnimeDetails(val id: Int) : Destination()

    @Serializable
    data object MainScreen : Destination()

    @Serializable
    data class RecommendedAnime(val animeIds: List<Int>) : Destination()
}