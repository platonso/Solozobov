package com.zahand0.moviesearch.ui.screen.popular

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.zahand0.moviesearch.R
import com.zahand0.moviesearch.domain.model.Country
import com.zahand0.moviesearch.domain.model.FilmDetails
import com.zahand0.moviesearch.domain.model.Genre
import com.zahand0.moviesearch.ui.theme.*
import java.util.*

@Composable
fun DetailsContent(
    filmDetails: FilmDetails
) {
    val scrollState = rememberScrollState()


        Column(
            modifier = Modifier.verticalScroll(scrollState)
        ) {
            BackgroundContent(
                imageUrl = filmDetails.posterUrl,
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.background)
                    .padding(horizontal = EXTRA_LARGE_PADDING)
                    .padding(vertical = LARGE_PADDING),
                verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
            ) {
                // TODO english title
                val title = filmDetails.nameRu ?: filmDetails.nameEn
                title?.let {
                    Text(
                        text = title,
                        color = MaterialTheme.colors.onBackground,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                val description = filmDetails.description
                description?.let {
                    Text(
                        text = description,
                        color = MaterialTheme.colors.onBackground.copy(alpha = 0.6f),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
                val genres =
                    filmDetails.genres.joinToString(separator = ", ") { it.genre.lowercase(Locale.ROOT) }
                if (genres.isNotBlank()) {
                    val genresAnnotatedString = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colors.onBackground.copy(alpha = 0.6f),
                                fontSize = 14.sp
                            )
                        ) {
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Medium
                                )
                            ) {
                                append(stringResource(id = R.string.genres))
                                append(" ")
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Normal
                                )
                            ) {
                                append(genres)
                            }
                        }
                    }
                    Text(
                        text = genresAnnotatedString
                    )
                }
                val countries = filmDetails.countries.joinToString(separator = ", ") { it.country }
                if (countries.isNotBlank()) {
                    val countriesAnnotatedString = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colors.onBackground.copy(alpha = 0.6f),
                                fontSize = 14.sp
                            )
                        ) {
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Medium
                                )
                            ) {
                                append(stringResource(id = R.string.countries))
                                append(" ")
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Normal
                                )
                            ) {
                                append(countries)
                            }
                        }
                    }
                    Text(
                        text = countriesAnnotatedString
                    )
                }
            }

        }
    }



@Composable
fun BackgroundContent(
    imageUrl: String
) {
    val painter = rememberAsyncImagePainter(
        model = imageUrl,
        placeholder = painterResource(id = R.drawable.image_placeholder),
        error = painterResource(id = R.drawable.image_placeholder)
    )
    Box(modifier = Modifier.fillMaxWidth()) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painter,
            contentDescription = stringResource(R.string.poster_image),
            contentScale = ContentScale.FillWidth
        )
    }
}


@Preview
@Composable
fun DetailsContentPreview() {
    MovieSearchTheme {
        DetailsContent(
            filmDetails = FilmDetails(
                countries = listOf(Country("Россия")),
                genres = listOf(Genre("Триллер")),
                nameEn = "The Matrix",
                nameRu = "Матрица",
                posterUrl = "",
                description = "Сопротивление собирает отряд для выполнения особой миссии - надо выкрасть чертежи самого совершенного и мертоносного оружия Империи. Не всем суждено вернуться домой, но герои готовы к этому, ведь на кону судьба Галактики",
                kinopoiskId = 1
            )
        )
    }
}