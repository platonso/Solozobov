package com.zahand0.moviesearch.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zahand0.moviesearch.util.Constants

@Entity(tableName = Constants.FILM_REMOTE_KEYS_DATABASE_TABLE)
data class FilmRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?,
    val lastUpdated: Long?,
    val indexInPage: Int
)