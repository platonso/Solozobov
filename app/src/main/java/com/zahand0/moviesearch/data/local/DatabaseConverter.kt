package com.zahand0.moviesearch.data.local

import androidx.room.TypeConverter
import com.zahand0.moviesearch.domain.model.Country
import com.zahand0.moviesearch.domain.model.Genre

class DatabaseConverter {

    private val separator = "*#*"

    @TypeConverter
    fun convertCountriesListToString(list: List<Country>): String {
        val stringBuilder = StringBuilder()
        for (item in list) {
            stringBuilder.append(item.country).append(separator)
        }
        stringBuilder.setLength(stringBuilder.length - separator.length)
        return stringBuilder.toString()
    }

    @TypeConverter
    fun convertStringToCountriesList(string: String): List<Country> {
        return string.split(separator).map { Country(it) }
    }

    @TypeConverter
    fun convertGenresListToString(list: List<Genre>): String {
        val stringBuilder = StringBuilder()
        for (item in list) {
            stringBuilder.append(item.genre).append(separator)
        }
        stringBuilder.setLength(stringBuilder.length - separator.length)
        return stringBuilder.toString()
    }

    @TypeConverter
    fun convertStringToGenresList(string: String): List<Genre> {
        return string.split(separator).map { Genre(it) }
    }

}