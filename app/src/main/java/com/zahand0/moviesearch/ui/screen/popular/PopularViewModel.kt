package com.zahand0.moviesearch.ui.screen.popular

import androidx.lifecycle.ViewModel
import com.zahand0.moviesearch.domain.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    useCases: UseCases
) : ViewModel() {
    val getAllFilms = useCases.getAllFilmsUseCase()
}