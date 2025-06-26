package co.feip.fefu2025

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
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
import co.feip.fefu2025.presentation.SearchScreen.SearchScreen
import co.feip.fefu2025.presentation.SearchScreen.SearchViewModel
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            HandleDeepLinks(navController, intent)

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
                        navigateToSearch = {
                            navController.navigate(Destination.SearchScreen)
                        },
                        onPaginate = viewModel::loadNextItems,
                        onRetry = viewModel::retry
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
                        navigateToRecommended = { id -> navController.navigate(Destination.RecommendedAnime(id)) },
                        onRetry = { viewModel.loadAnimeDetails() }
                    )
                }

                composable<Destination.RecommendedAnime> { backStackEntry ->
                    val args = backStackEntry.toRoute<Destination.RecommendedAnime>()
                    val viewModel: RecommendedAnimeScreenViewModel = viewModel(
                        factory = object : ViewModelProvider.Factory {
                            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                                return RecommendedAnimeScreenViewModel(args.animeId) as T
                            }
                        }
                    )
                    RecommendedAnimeScreen(
                        state = viewModel.state,
                        navController = navController,
                        navigateToDetails = { id -> navController.navigate(Destination.AnimeDetails(id)) },
                        onRetry = viewModel::onRetry,
                        onPaginate = viewModel::loadNextPage
                    )
                }

                composable<Destination.SearchScreen> {
                    val viewModel: SearchViewModel = viewModel()
                    val mainViewModel: MainAnimeScreenViewModel = viewModel()
                    val searchQuery = mainViewModel.state.searchQuery

                    SearchScreen(
                        searchQuery = searchQuery,
                        onSearchQueryChange = { query ->
                            mainViewModel.onQueryChange(query)
                            viewModel.onQueryChanged(query)
                        },
                        searchResults = viewModel.state.searchResults,
                        isLoading = viewModel.state.isLoading,
                        error = viewModel.state.error,
                        onBackClick = { navController.popBackStack() },
                        navigateToDetails = { id -> navController.navigate(Destination.AnimeDetails(id)) },
                        isLoadingNextPage = viewModel.state.isLoadingNextPage,
                        paginationError = viewModel.state.paginationError,
                        onPaginate = viewModel::loadNextPage,
                        onRetryPagination = viewModel::loadNextPage
                    )
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
    }
}

@Composable
fun HandleDeepLinks(navController: NavHostController, intent: Intent) {
    val currentIntent = remember { intent }

    LaunchedEffect(currentIntent) {
        val data: Uri? = currentIntent.data
        if (data != null && data.scheme == "mysuperapp" && data.host == "anime") {
            val pathSegments = data.pathSegments
            if (pathSegments.isNotEmpty()) {
                val id = pathSegments.last().toIntOrNull()
                id?.let {
                    navController.navigate(Destination.AnimeDetails(it)) {
                        popUpTo(Destination.MainScreen) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
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
    data class RecommendedAnime(val animeId: Int) : Destination()

    @Serializable
    data object SearchScreen : Destination()
}