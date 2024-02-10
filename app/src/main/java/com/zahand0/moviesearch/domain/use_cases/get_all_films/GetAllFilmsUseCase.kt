package com.zahand0.moviesearch.domain.use_cases.get_all_films

import androidx.paging.PagingData
import com.zahand0.moviesearch.data.repository.Repository
import com.zahand0.moviesearch.domain.model.Film
import kotlinx.coroutines.flow.Flow

class GetAllFilmsUseCase(
    private val repository: Repository
) {
    operator fun invoke(): Flow<PagingData<Film>> {
        return repository.getAllFilms()
    }
}