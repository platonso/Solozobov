package com.zahand0.moviesearch.data.remote

import com.zahand0.moviesearch.domain.model.Film
import com.zahand0.moviesearch.domain.model.FilmDetails
import com.zahand0.moviesearch.domain.model.FilmListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface KinopoiskUnofficialAPI {

    @GET("/api/v2.2/films/top")
    suspend fun getAllFilms(
        @Query("page") page: Int = 1,
        @Query("type") type: String = "TOP_100_POPULAR_FILMS",
    ): FilmListResponse

    @GET(" /api/v2.2/films/{id}")
    suspend fun getFilm(
        @Path("id") id: Int
    ): FilmDetails

}