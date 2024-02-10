package com.zahand0.moviesearch.navigation

import com.zahand0.moviesearch.util.Constants

sealed class Screen(val route: String) {
    object Popular : Screen(route = "popular_screen")
    object Search : Screen(route = "search_screen")
    object Movie : Screen(route = "movie_screen/{${Constants.MOVIE_SCREEN_ARGUMENT_KEY}}") {
        fun passId(id: Int): String {
            return "movie_screen/$id"
        }
    }
}
