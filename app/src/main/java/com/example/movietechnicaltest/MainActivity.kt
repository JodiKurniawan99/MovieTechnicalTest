package com.example.movietechnicaltest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.feature.detail.MovieDetailScreen
import com.example.feature.detail.MovieDetailViewModel
import com.example.feature.genres.GenresScreen
import com.example.feature.genres.GenresViewModel
import com.example.feature.movies.MoviesScreen
import com.example.feature.movies.MoviesViewModel
import com.example.movietechnicaltest.ui.theme.MovieTechnicalTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieTechnicalTestTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "genres") {
                        composable("genres") {
                            val viewModel: GenresViewModel = hiltViewModel()
                            GenresScreen(
                                viewModel = viewModel,
                                onGenFreClick = { genreId, genreName ->
                                    navController.navigate("movies/$genreId/$genreName")
                                }
                            )
                        }
                        composable(
                            route = "movies/{genreId}/{genreName}",
                            arguments = listOf(
                                navArgument("genreId") { type = NavType.StringType },
                                navArgument("genreName") { type = NavType.StringType }
                            )
                        ) { backStackEntry ->
                            val genreName = backStackEntry.arguments?.getString("genreName") ?: ""
                            val viewModel: MoviesViewModel = hiltViewModel()
                            MoviesScreen(
                                genreName = genreName,
                                viewModel = viewModel,
                                onMovieClick = { movieId ->
                                    navController.navigate("detail/$movieId")
                                },
                                onBackClick = {
                                    navController.popBackStack()
                                }
                            )
                        }
                        composable(
                            route = "detail/{movieId}",
                            arguments = listOf(
                                navArgument("movieId") { type = NavType.IntType }
                            )
                        ) {
                            val viewModel: MovieDetailViewModel = hiltViewModel()
                            MovieDetailScreen(
                                viewModel = viewModel,
                                onBackClick = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
