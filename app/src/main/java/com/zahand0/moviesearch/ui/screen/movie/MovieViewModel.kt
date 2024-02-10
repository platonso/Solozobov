package com.zahand0.moviesearch.ui.screen.movie

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zahand0.moviesearch.domain.model.Film
import com.zahand0.moviesearch.domain.model.FilmDetails
import com.zahand0.moviesearch.domain.model.Resource
import com.zahand0.moviesearch.domain.use_cases.UseCases
import com.zahand0.moviesearch.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    useCases: UseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val getFilmUseCase = useCases.getSelectedFilmUseCase

    private val _selectedFilm: MutableState<Resource<FilmDetails>> = mutableStateOf(Resource.Loading())
    val selectedFilm: State<Resource<FilmDetails>> = _selectedFilm

    fun refreshFilm() {
        viewModelScope.launch(Dispatchers.IO) {
            val filmId = savedStateHandle.get<Int>(Constants.MOVIE_SCREEN_ARGUMENT_KEY)
            _selectedFilm.value = filmId?.let { getFilmUseCase(filmId) } ?: Resource.Error(message = null)
        }
    }
}