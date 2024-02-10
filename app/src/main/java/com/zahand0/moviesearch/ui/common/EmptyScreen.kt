package com.zahand0.moviesearch.ui.common

import android.content.Context
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.zahand0.moviesearch.R
import com.zahand0.moviesearch.domain.model.Film
import com.zahand0.moviesearch.ui.theme.MovieSearchTheme
import com.zahand0.moviesearch.ui.theme.NETWORK_ERROR_ICON_HEIGHT
import com.zahand0.moviesearch.ui.theme.SMALL_PADDING
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

@Composable
fun EmptyScreen(
    error: LoadState.Error? = null,
    films: LazyPagingItems<Film>? = null
) {
    val context = LocalContext.current
    var message by remember {
        mutableStateOf(
            context.getString(R.string.find_film)
        )
    }
    var icon by remember {
        mutableStateOf(R.drawable.ic_search_document)
    }

    if (error != null) {
        message = parseErrorMessage(
            error = error,
            context = context
        )
        icon = R.drawable.network_error
    }

    var startAnimation by remember {
        mutableStateOf(false)
    }
    val alphaAnim by animateFloatAsState(
        targetValue = if (startAnimation)
            ContentAlpha.disabled else 0f,
        animationSpec = tween(
            durationMillis = 1000
        )
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
    }

    EmptyContent(
        message = message,
        icon = icon,
        alphaAnim = alphaAnim,
        films = films,
        error = error
    )
}

@Composable
fun EmptyContent(
    message: String,
    @DrawableRes icon: Int,
    alphaAnim: Float,
    films: LazyPagingItems<Film>? = null,
    error: LoadState.Error? = null
) {

    var isRefreshing by remember { mutableStateOf(false) }
    SwipeRefresh(
        swipeEnabled = error != null,
        state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
        onRefresh = {
            isRefreshing = true
            films?.refresh()
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
                painter = painterResource(id = icon),
                contentDescription = stringResource(R.string.network_error_icon),
                tint = MaterialTheme.colors.primary
            )
            Text(
                modifier = Modifier
                    .padding(top = SMALL_PADDING)
                    .alpha(1f),
                text = message,
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            )
        }
    }
}

private fun parseErrorMessage(
    error: LoadState.Error,
    context: Context
): String {
    return when (error.error) {
        is SocketTimeoutException -> {
            context.getString(R.string.server_unavailable)
        }
        is ConnectException, is UnknownHostException -> {
            context.getString(R.string.internet_unavailable)
        }
        else -> {
            context.getString(R.string.unknown_error)
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun EmptyScreenPreview() {
    MovieSearchTheme() {
        Surface(color = MaterialTheme.colors.background) {
            EmptyContent(
                message = "Internet Unavailable.",
                icon = R.drawable.network_error,
                alphaAnim = ContentAlpha.disabled
            )
        }
    }
}
