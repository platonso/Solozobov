package com.zahand0.moviesearch.navigation

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.zahand0.moviesearch.ui.screen.movie.MovieScreen
import com.zahand0.moviesearch.ui.screen.popular.PopularScreen
import com.zahand0.moviesearch.util.Constants

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.Popular.route
    ) {
        composable(
            route = Screen.Popular.route,
            enterTransition = { scaleIn() },
            exitTransition = { scaleOut() }
        ) {
            PopularScreen(navController = navController)
        }
        composable(
            route = Screen.Search.route,
            enterTransition = { scaleIn() },
            exitTransition = { scaleOut() }
        ) {

        }
        composable(
            route = Screen.Movie.route,
            arguments = listOf(navArgument(Constants.MOVIE_SCREEN_ARGUMENT_KEY) {
                type = NavType.IntType
            }),
            enterTransition = { slideInHorizontally { it } },
            exitTransition = { slideOutHorizontally { it } }
        ) {
            MovieScreen(navController = navController)
        }
    }
}