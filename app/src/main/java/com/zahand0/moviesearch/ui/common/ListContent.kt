package com.zahand0.moviesearch.ui.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberAsyncImagePainter
import com.zahand0.moviesearch.R
import com.zahand0.moviesearch.domain.model.Country
import com.zahand0.moviesearch.domain.model.Film
import com.zahand0.moviesearch.domain.model.Genre
import com.zahand0.moviesearch.ui.components.ShimmerEffect
import com.zahand0.moviesearch.ui.theme.EXTRA_SMALL_PADDING
import com.zahand0.moviesearch.ui.theme.FILM_ITEM_HEIGHT
import com.zahand0.moviesearch.ui.theme.MEDIUM_PADDING
import com.zahand0.moviesearch.ui.theme.MovieSearchTheme
import java.util.*

@Composable
fun ListContent(
    modifier: Modifier = Modifier,
    films: LazyPagingItems<Film>,
    onItemClick: (Int) -> Unit,
    nestedScrollConnection: NestedScrollConnection
) {
    Log.d("ListContent", films.loadState.toString())
    val result = handlePagingResult(films = films)
    if (result) {
        LazyColumn(
            modifier = modifier
                .nestedScroll(nestedScrollConnection),
            contentPadding = PaddingValues(MEDIUM_PADDING),
            verticalArrangement = Arrangement.spacedBy(MEDIUM_PADDING),
        ) {
            items(
                items = films,
                key = { film ->
                    film.filmId
                }
            ) { film ->
                film?.let {
                    FilmItem(
                        film = it,
                        onItemClick = onItemClick
                    )
                }
            }
        }
    }

}

@Composable
fun handlePagingResult(
    films: LazyPagingItems<Film>
): Boolean {
    films.apply {
        val error = when {
            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
            else -> null
        }
        return when {
            loadState.refresh is LoadState.Loading -> {
                ShimmerEffect()
                false
            }
            error != null -> {
                EmptyScreen(
                    error = error,
                    films = films
                )
                false
            }
            films.itemCount < 1 -> {
                EmptyScreen()
                false
            }
            else -> true
        }
    }
}

@Composable
fun FilmItem(
    film: Film,
    onItemClick: (Int) -> Unit
) {
    val painter = rememberAsyncImagePainter(
        model = film.posterUrlPreview,
        placeholder = painterResource(id = R.drawable.image_placeholder),
        error = painterResource(id = R.drawable.image_placeholder)
    )
    Box(
        modifier = Modifier
            .height(FILM_ITEM_HEIGHT)
            .shadow(
                elevation = 5.dp,
                shape = RoundedCornerShape(MEDIUM_PADDING),
                spotColor = if (isSystemInDarkTheme())
                    MaterialTheme.colors.onBackground.copy(alpha = 0.5f)
                else
                    MaterialTheme.colors.onBackground
            )
            .clickable {
                onItemClick(film.filmId)
            },
        contentAlignment = Alignment.BottomStart
    ) {
        Surface(
            shape = RoundedCornerShape(MEDIUM_PADDING),
        ) {
            Row(
                modifier = Modifier.padding(MEDIUM_PADDING)
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(0.15f),
                    contentAlignment = Alignment.Center
                ) {
                    Surface(
                        shape = RoundedCornerShape(EXTRA_SMALL_PADDING)
                    ) {
                        Image(
                            modifier = Modifier
                                .fillMaxHeight(),
                            painter = painter,
                            contentDescription = film.nameEn,
                            contentScale = ContentScale.FillHeight
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = EXTRA_SMALL_PADDING)
                        .padding(horizontal = MEDIUM_PADDING),
                    verticalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    // TODO check language and set film name
                    Text(
                        text = film.nameRu ?: film.nameEn ?: "",
                        color = MaterialTheme.colors.onBackground,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    val genresYearText = buildString {
                        val genres = film.genres.joinToString(separator = ", ") { genre ->
                            genre.genre.replaceFirstChar {
                                if (it.isLowerCase())
                                    it.titlecase(locale = Locale.getDefault())
                                else
                                    it.toString()
                            }
                        }
                        append(genres)
                        film.year?.let { year ->
                            append(" ($year)")
                        }
                    }
                    Text(
                        text = genresYearText,
                        color = MaterialTheme.colors.onBackground.copy(alpha = 0.6f),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )

                }
            }
        }

    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HeroItemPreview() {
    MovieSearchTheme() {
        FilmItem(
            film = Film(
                countries = listOf(Country("Россия")),
                filmId = 1,
                filmLength = "1 ч. 10 мин.",
                genres = listOf(Genre("Триллер")),
                nameEn = "The Matrix",
                nameRu = "Матрица",
                posterUrl = "",
                posterUrlPreview = "",
                rating = "7.56",
                ratingVoteCount = 4654,
                year = "1999"
            ),
            onItemClick = {}
        )
    }

}