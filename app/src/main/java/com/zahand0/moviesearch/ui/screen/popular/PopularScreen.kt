package com.zahand0.moviesearch.ui.screen.popular

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.zahand0.moviesearch.R
import com.zahand0.moviesearch.navigation.Screen
import com.zahand0.moviesearch.ui.common.CommonTopBar
import com.zahand0.moviesearch.ui.common.ListContent

@Composable
fun PopularScreen(
    navController: NavHostController,
    viewModel: PopularViewModel = hiltViewModel()
) {
    val allFilms = viewModel.getAllFilms.collectAsLazyPagingItems()
    var scrollValue by remember {
        mutableStateOf(0f)
    }
    var isScrolled by remember {
        mutableStateOf(false)
    }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                scrollValue += consumed.y
//                Log.d("PopularScreen", "onPostScroll: scrollValue $scrollValue")
                isScrolled = scrollValue !in -1f..1f
                return super.onPostScroll(consumed, available, source)
            }
        }
    }

    Scaffold(
        topBar = {
            CommonTopBar(
                title = stringResource(R.string.popular),
                onSearchClicked = {
//                    navController.navigate(Screen.Search.route)
                },
                isScrolled = isScrolled
            )
        },
        content = { padding ->
            ListContent(
                modifier = Modifier.padding(padding),
                films = allFilms,
                onItemClick = { id ->
                    navController.navigate(Screen.Movie.passId(id))
                },
                nestedScrollConnection = nestedScrollConnection
            )
        },
        backgroundColor = MaterialTheme.colors.surface
    )
}