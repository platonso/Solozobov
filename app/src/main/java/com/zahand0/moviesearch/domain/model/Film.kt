package com.zahand0.moviesearch.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zahand0.moviesearch.util.Constants

@Entity(tableName = Constants.FILM_DATABASE_TABLE)
data class Film(
    val countries: List<Country>,
    @PrimaryKey(autoGenerate = false)
    val filmId: Int,
    val filmLength: String?,
    val genres: List<Genre>,
    val nameEn: String?,
    val nameRu: String?,
    val posterUrl: String,
    val posterUrlPreview: String,
    val rating: String?,
//    val ratingChange: Any,
    val ratingVoteCount: Int?,
    val year: String?
)