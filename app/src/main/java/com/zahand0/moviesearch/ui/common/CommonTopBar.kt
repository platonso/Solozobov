package com.zahand0.moviesearch.ui.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zahand0.moviesearch.ui.theme.MovieSearchTheme
import com.zahand0.moviesearch.R

@Composable
fun CommonTopBar(
    title: String,
    isScrolled: Boolean,
    onSearchClicked: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                fontSize = 25.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colors.onBackground
            )
        },
        backgroundColor = MaterialTheme.colors.background,
        actions = {
            IconButton(onClick = onSearchClicked) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.search_icon),
                    tint = MaterialTheme.colors.primary
                )
            }
        },
        elevation = if (isScrolled && !isSystemInDarkTheme()) 4.dp else 0.dp
    )
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HomeTopBarPreview() {
    MovieSearchTheme() {
        CommonTopBar(
            title = "Popular",
            isScrolled = false
        ) {}
    }
}
