package com.zahand0.moviesearch.domain.model

data class FilmListResponse(
    val films: List<Film>,
    val pagesCount: Int
)