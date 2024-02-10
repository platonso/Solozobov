package com.zahand0.moviesearch.domain.repository

import com.zahand0.moviesearch.domain.model.Film
import com.zahand0.moviesearch.domain.model.FilmDetails

interface LocalDataSource {

    suspend fun getSelectedFilm(filmId: Int): FilmDetails?

    suspend fun insertFilm(filmDetails: FilmDetails)
}