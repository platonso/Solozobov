package com.zahand0.moviesearch.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zahand0.moviesearch.util.Constants

@Entity(tableName = Constants.FILM_DETAILS_DATABASE_TABLE)
data class FilmDetails(
    val countries: List<Country>,
    val description: String?,
    val genres: List<Genre>,
    @PrimaryKey(autoGenerate = false)
    val kinopoiskId: Int,
    val nameEn: String?,
    val nameRu: String?,
    val posterUrl: String
)