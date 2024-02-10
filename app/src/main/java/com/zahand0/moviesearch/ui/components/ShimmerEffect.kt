package com.zahand0.moviesearch.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.*
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zahand0.moviesearch.ui.theme.*

@Composable
fun ShimmerEffect() {
    LazyColumn(
        contentPadding = PaddingValues(SMALL_PADDING),
        verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
    ) {
        items(count = 6) {
            AnimatedShimmerItem()
        }
    }
}

@Composable
fun AnimatedShimmerItem() {
    val transition = rememberInfiniteTransition()
    val animateFloat by transition.animateFloat(
        initialValue = 1f,
        targetValue = 0.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 300,
                easing = FastOutLinearInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )
    ShimmerItem(alpha = animateFloat)
}

@Composable
fun ShimmerItem(alpha: Float) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(FILM_ITEM_HEIGHT)
            .shadow(
                elevation = 5.dp,
                shape = RoundedCornerShape(MEDIUM_PADDING),
                spotColor = MaterialTheme.colors.onBackground
            ),
        color = MaterialTheme.colors.background,
        shape = RoundedCornerShape(LARGE_PADDING)
    ) {
        Row(
            modifier = Modifier.padding(MEDIUM_PADDING)
        ) {
            Surface(
                modifier = Modifier
                    .alpha(alpha)
                    .fillMaxWidth(0.15f)
                    .fillMaxHeight(),
                color = if (isSystemInDarkTheme())
                    ShimmerDarkGray else ShimmerMediumGray,
                shape = RoundedCornerShape(EXTRA_SMALL_PADDING)
            ) {}

            Column(
                modifier = Modifier
                    .padding(top = EXTRA_SMALL_PADDING)
                    .padding(horizontal = MEDIUM_PADDING),
                verticalArrangement = Arrangement.Bottom
            ) {
                Surface(
                    modifier = Modifier
                        .alpha(alpha)
                        .fillMaxWidth(0.6f)
                        .height(NAME_PLACEHOLDER_HEIGHT),
                    color = if (isSystemInDarkTheme())
                        ShimmerDarkGray else ShimmerMediumGray,
                    shape = RoundedCornerShape(SMALL_PADDING)
                ) {}
                Spacer(modifier = Modifier.height(3.dp))
                Surface(
                    modifier = Modifier
                        .alpha(alpha)
                        .fillMaxWidth(0.8f)
                        .height(ABOUT_PLACEHOLDER_HEIGHT),
                    color = if (isSystemInDarkTheme())
                        ShimmerDarkGray else ShimmerMediumGray,
                    shape = RoundedCornerShape(SMALL_PADDING)
                ) {}
                Spacer(modifier = Modifier.height(3.dp))
                Surface(
                    modifier = Modifier
                        .alpha(alpha)
                        .fillMaxWidth(0.3f)
                        .height(ABOUT_PLACEHOLDER_HEIGHT),
                    color = if (isSystemInDarkTheme())
                        ShimmerDarkGray else ShimmerMediumGray,
                    shape = RoundedCornerShape(SMALL_PADDING)
                ) {}
            }
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ShimmerItemPreview() {
    MovieSearchTheme() {
        AnimatedShimmerItem()
    }
}