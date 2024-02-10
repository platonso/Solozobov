package com.zahand0.moviesearch.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zahand0.moviesearch.domain.model.Film

@Dao
interface FilmDao {

    @Query("SELECT f.* FROM film_table as f JOIN film_remote_keys_table AS r on f.filmId = r.id ORDER BY r.prevPage, r.indexInPage ASC")
    fun getAllFilms(): PagingSource<Int, Film>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFilms(films: List<Film>)

    @Query("DELETE FROM film_table")
    suspend fun deleteAllFilms()

}