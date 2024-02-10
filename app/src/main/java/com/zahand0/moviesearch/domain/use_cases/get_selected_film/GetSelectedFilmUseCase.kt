package com.zahand0.moviesearch.domain.use_cases.get_selected_film

import com.zahand0.moviesearch.data.repository.Repository
import com.zahand0.moviesearch.domain.model.FilmDetails
import com.zahand0.moviesearch.domain.model.Resource

class GetSelectedFilmUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(id: Int): Resource<FilmDetails> {
        return repository.getFilm(id)
    }
}