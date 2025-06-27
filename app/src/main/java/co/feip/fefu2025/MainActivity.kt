package co.feip.fefu2025

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
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
import co.feip.fefu2025.presentation.FavoriteDetailsScreen.FavoriteDetailsScreen
import co.feip.fefu2025.presentation.FavoriteDetailsScreen.FavoriteDetailsViewModel
import co.feip.fefu2025.presentation.FavoritesScreen.FavoritesScreen
import co.feip.fefu2025.presentation.FavoritesScreen.FavoritesViewModel
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
            val application = LocalContext.current.applicationContext as Application

            NavHost(
                navController = navController,
                startDestination = Destination.MainScreen
            ) {
                composable<Destination.MainScreen> {
                    val viewModel: MainAnimeScreenViewModel = viewModel(factory = ViewModelFactory(application))
                    MainAnimeScreen(
                        state = viewModel.state,
                        onQueryChange = viewModel::onQueryChange,
                        onPaginate = viewModel::loadNextItems,
                        onRetry = viewModel::retry,
                        navigateTODetails = { id -> navController.navigate(Destination.AnimeDetails(id)) },
                        navigateToSearch = { navController.navigate(Destination.SearchScreen) },
                        navigateToFavorites = { navController.navigate(Destination.Favorites) }
                    )
                }

                composable<Destination.Favorites> {
                    val viewModel: FavoritesViewModel = viewModel(factory = ViewModelFactory(application))
                    val state by viewModel.state.collectAsState()
                    FavoritesScreen(
                        state = state,
                        navigateToMain = { navController.navigate(Destination.MainScreen) { popUpTo(0)} },
                        navigateToDetails = { id -> navController.navigate(Destination.FavoriteDetails(id)) }
                    )
                }

                composable<Destination.AnimeDetails> { backStackEntry ->
                    val args = backStackEntry.toRoute<Destination.AnimeDetails>()
                    val factory = AnimeDetailsViewModelFactory(application, args.id)
                    val viewModel: AnimeDetailScreenViewModel = viewModel(factory = factory)
                    val isFavorite by viewModel.isFavorite.collectAsState()
                    AnimeDetailScreen(
                        state = viewModel.state,
                        isFavorite = isFavorite,
                        onFavoriteClick = viewModel::onFavoriteClick,
                        onBackClick = { navController.popBackStack() },
                        navigateToDetails = { detailId -> navController.navigate(Destination.AnimeDetails(detailId)) },
                        navigateToRecommended = { animeId -> navController.navigate(Destination.RecommendedAnime(animeId)) },
                        onRetry = { viewModel.loadAnimeDetails() }
                    )
                }

                composable<Destination.RecommendedAnime> { backStackEntry ->
                    val args = backStackEntry.toRoute<Destination.RecommendedAnime>()
                    val viewModel: RecommendedAnimeScreenViewModel = viewModel(factory = ViewModelFactory(application, args.animeId))
                    RecommendedAnimeScreen(
                        state = viewModel.state,
                        navController = navController,
                        navigateToDetails = { id -> navController.navigate(Destination.AnimeDetails(id)) },
                        onRetry = viewModel::onRetry,
                        onPaginate = viewModel::loadNextPage
                    )
                }

                composable<Destination.SearchScreen> {
                    val searchViewModel: SearchViewModel = viewModel(factory = ViewModelFactory(application))
                    val mainViewModel: MainAnimeScreenViewModel = viewModel(factory = ViewModelFactory(application))
                    SearchScreen(
                        searchQuery = searchViewModel.state.currentQuery,
                        onSearchQueryChange = { query ->
                            mainViewModel.onQueryChange(query)
                            searchViewModel.onQueryChanged(query)
                        },
                        searchResults = searchViewModel.state.searchResults,
                        isLoading = searchViewModel.state.isLoading,
                        error = searchViewModel.state.error,
                        onBackClick = { navController.popBackStack() },
                        navigateToDetails = { id -> navController.navigate(Destination.AnimeDetails(id)) },
                        isLoadingNextPage = searchViewModel.state.isLoadingNextPage,
                        paginationError = searchViewModel.state.paginationError,
                        onPaginate = searchViewModel::loadNextPage,
                        onRetryPagination = searchViewModel::loadNextPage
                    )
                }

                composable<Destination.FavoriteDetails> { backStackEntry ->
                    val args = backStackEntry.toRoute<Destination.FavoriteDetails>()
                    val viewModel: FavoriteDetailsViewModel = viewModel(factory = ViewModelFactory(application, args.animeId))
                    val state by viewModel.state.collectAsState()

                    LaunchedEffect(Unit) {
                        viewModel.eventFlow.collect { event ->
                            when (event) {
                                is FavoriteDetailsViewModel.UiEvent.NavigateBack -> {
                                    navController.popBackStack()
                                }
                            }
                        }
                    }

                    FavoriteDetailsScreen(
                        state = state,
                        onBackClick = { navController.popBackStack() },
                        onRemoveClick = viewModel::onRemoveFromFavorites
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


class ViewModelFactory(private val application: Application, private val id: Int? = null) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainAnimeScreenViewModel::class.java) -> MainAnimeScreenViewModel(application) as T
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> SearchViewModel(application) as T
            modelClass.isAssignableFrom(FavoritesViewModel::class.java) -> FavoritesViewModel(application) as T
            modelClass.isAssignableFrom(RecommendedAnimeScreenViewModel::class.java) -> RecommendedAnimeScreenViewModel(application, id!!) as T
            modelClass.isAssignableFrom(FavoriteDetailsViewModel::class.java) -> FavoriteDetailsViewModel(application, id!!) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
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
                id?.let { navController.navigate(Destination.AnimeDetails(it)) }
            }
        }
    }
}

@Serializable
sealed class Destination {
    @Serializable
    data object MainScreen : Destination()

    @Serializable
    data object Favorites : Destination()

    @Serializable
    data class AnimeDetails(val id: Int) : Destination()

    @Serializable
    data class RecommendedAnime(val animeId: Int) : Destination()

    @Serializable
    data object SearchScreen : Destination()

    @Serializable
    data class FavoriteDetails(val animeId: Int) : Destination()
}