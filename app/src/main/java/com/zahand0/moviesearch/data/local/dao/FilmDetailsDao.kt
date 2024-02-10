package com.zahand0.moviesearch.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zahand0.moviesearch.domain.model.Film
import com.zahand0.moviesearch.domain.model.FilmDetails

@Dao
interface FilmDetailsDao {

    @Query("SELECT * FROM film_details_table WHERE kinopoiskId=:filmId")
    fun getSelectedFilm(filmId: Int): FilmDetails?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: FilmDetails)
}