package com.zahand0.moviesearch.data.repository

import android.util.Log
import androidx.paging.PagingData
import com.zahand0.moviesearch.domain.model.Film
import com.zahand0.moviesearch.domain.model.FilmDetails
import com.zahand0.moviesearch.domain.model.Resource
import com.zahand0.moviesearch.domain.repository.LocalDataSource
import com.zahand0.moviesearch.domain.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val remote: RemoteDataSource,
    private val local: LocalDataSource
) {

    fun getAllFilms(): Flow<PagingData<Film>> {
        return remote.getAllFilms()
    }

    suspend fun getFilm(id: Int): Resource<FilmDetails> {
        var film = local.getSelectedFilm(id)
        try {
            if (film == null) {
                film = remote.getFilm(id)
                local.insertFilm(film)
            }
        } catch (e: Exception) {
            Log.e("Repository", "getFilm: ", e)
        }
        return if (film == null)
            Resource.Error(message = null)
        else
            Resource.Success(film)
    }
//    fun searchFilms(query: String): Flow<PagingData<Film>> {
//        return remote.searchFilms(query = query)
//    }

}