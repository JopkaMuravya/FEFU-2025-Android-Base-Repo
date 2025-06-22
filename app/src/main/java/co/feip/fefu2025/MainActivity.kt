package co.feip.fefu2025

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
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
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{



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
                composable<Destination.AnimeDetails> {
                    val id = it.toRoute<Destination.AnimeDetails>().id
                    println(id)
                    val factory = AnimeDetailsViewModelFactory(id)
                    val viewModel: AnimeDetailScreenViewModel = viewModel(factory = factory)
                    val state = viewModel.state
                    AnimeDetailScreen(
                        state = state,
                        modifier = Modifier,
                        navigateToDetails = { id -> navController.navigate(Destination.AnimeDetails(id)) },
                    )
                }
            }

        }

    }
}

sealed class Destination{
    @Serializable
    data class AnimeDetails(val id: Int): Destination()
    @Serializable
    data object MainScreen: Destination()
}
