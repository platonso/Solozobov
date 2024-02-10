package com.zahand0.moviesearch.domain.use_cases

import com.zahand0.moviesearch.domain.use_cases.get_all_films.GetAllFilmsUseCase
import com.zahand0.moviesearch.domain.use_cases.get_selected_film.GetSelectedFilmUseCase

data class UseCases(
    val getAllFilmsUseCase: GetAllFilmsUseCase,
    val getSelectedFilmUseCase: GetSelectedFilmUseCase
)
