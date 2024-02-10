package com.zahand0.moviesearch.ui.screen.movie

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.zahand0.moviesearch.R
import com.zahand0.moviesearch.domain.model.Resource
import com.zahand0.moviesearch.ui.screen.popular.DetailsContent
import com.zahand0.moviesearch.ui.theme.*

@Composable
fun MovieScreen(
    navController: NavHostController,
    viewModel: MovieViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.refreshFilm()
    }
    val filmDetails by viewModel.selectedFilm
    Box {
        when (filmDetails) {
            is Resource.Error -> {
                var isRefreshing by remember { mutableStateOf(false) }
                SwipeRefresh(
                    swipeEnabled = true,
                    state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
                    onRefresh = {
                        isRefreshing = true
                        viewModel.refreshFilm()
                        isRefreshing = false
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(NETWORK_ERROR_ICON_HEIGHT)
                                .alpha(1f),
                            painter = painterResource(id = R.drawable.network_error),
                            contentDescription = stringResource(R.string.network_error_icon),
                            tint = MaterialTheme.colors.primary
                        )
                        Text(
                            modifier = Modifier
                                .padding(top = SMALL_PADDING)
                                .alpha(1f),
                            text = stringResource(id = R.string.internet_unavailable),
                            color = MaterialTheme.colors.primary,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Medium,
                            fontSize = MaterialTheme.typography.subtitle1.fontSize
                        )
                    }
                }
            }
            is Resource.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth(0.3f),
                        strokeWidth = 10.dp
                    )
                }
            }
            is Resource.Success -> {
                filmDetails.data?.let {
                    DetailsContent(
                        filmDetails = it
                    )
                }
            }
        }
        filmDetails?.let { details ->

        }
        if (filmDetails == null) {

        }
        Row(
            modifier = Modifier
                .padding(horizontal = MEDIUM_PADDING)
                .padding(vertical = EXTRA_LARGE_PADDING)
        ) {
            IconButton(
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Icon(
                    modifier = Modifier.size(INFO_ICON_SIZE),
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back_icon),
                    tint = MaterialTheme.colors.primary
                )
            }
        }
    }
    

}