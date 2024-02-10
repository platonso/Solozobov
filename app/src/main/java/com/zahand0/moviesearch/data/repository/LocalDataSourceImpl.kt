package com.zahand0.moviesearch.data.repository

import com.zahand0.moviesearch.data.local.FilmDatabase
import com.zahand0.moviesearch.domain.model.Film
import com.zahand0.moviesearch.domain.model.FilmDetails
import com.zahand0.moviesearch.domain.repository.LocalDataSource

class LocalDataSourceImpl(filmDatabase: FilmDatabase): LocalDataSource {

    private val filmDetailsDao = filmDatabase.filmDetailsDao()

    override suspend fun getSelectedFilm(filmId: Int): FilmDetails? {
        return filmDetailsDao.getSelectedFilm(filmId = filmId)
    }

    override suspend fun insertFilm(filmDetails: FilmDetails) {
        filmDetailsDao.insert(filmDetails)
    }
}