package com.zahand0.moviesearch.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.zahand0.moviesearch.data.local.FilmDatabase
import com.zahand0.moviesearch.data.paging_source.FilmRemoteMediator
import com.zahand0.moviesearch.data.remote.KinopoiskUnofficialAPI
import com.zahand0.moviesearch.domain.model.Film
import com.zahand0.moviesearch.domain.model.FilmDetails
import com.zahand0.moviesearch.domain.repository.RemoteDataSource
import com.zahand0.moviesearch.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@ExperimentalPagingApi
class RemoteDataSourceImpl(
    private val kinopoiskUnofficialAPI: KinopoiskUnofficialAPI,
    private val filmDatabase: FilmDatabase
) : RemoteDataSource {

    private val filmDao = filmDatabase.filmDao()

    override fun getAllFilms(): Flow<PagingData<Film>> {
        val pagingSourceFactory = { filmDao.getAllFilms() }
        return Pager(
            config = PagingConfig(
                pageSize = Constants.ITEMS_PER_PAGE,
                initialLoadSize = Constants.ITEMS_INITIAL_LOAD
            ),
            remoteMediator = FilmRemoteMediator(
                kinopoiskUnofficialAPI = kinopoiskUnofficialAPI,
                filmDatabase = filmDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

//    override fun searchFilms(query: String): Flow<PagingData<Film>> {
//        TODO("Not yet implemented")
//    }

    override suspend fun getFilm(id: Int): FilmDetails {
        return kinopoiskUnofficialAPI.getFilm(id = id)
    }
}