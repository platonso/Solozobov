package com.zahand0.moviesearch.domain.repository

import androidx.paging.PagingData
import com.zahand0.moviesearch.domain.model.Film
import com.zahand0.moviesearch.domain.model.FilmDetails
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    fun getAllFilms(): Flow<PagingData<Film>>
//    fun searchFilms(query: String): Flow<PagingData<Film>>
    suspend fun getFilm(id: Int): FilmDetails
}