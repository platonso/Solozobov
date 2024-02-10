package com.zahand0.moviesearch.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zahand0.moviesearch.domain.model.FilmRemoteKeys

@Dao
interface FilmRemoteKeysDao {

    @Query("SELECT * FROM film_remote_keys_table WHERE id = :filmId")
    suspend fun getRemoteKeys(filmId: Int): FilmRemoteKeys?

    @Query("SELECT * FROM film_remote_keys_table WHERE prevPage IS NULL AND indexInPage = 0")
    suspend fun getFirstRemoteKeys(): FilmRemoteKeys?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(filmRemoteKeys: List<FilmRemoteKeys>)

    @Query("DELETE FROM film_remote_keys_table")
    suspend fun deleteAllRemoteKeys()
}