package com.dbesar.ditflix.ui.screen.home

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.dbesar.ditflix.domain.model.MovieDetail
import com.dbesar.ditflix.domain.model.Trailer
import com.dbesar.ditflix.domain.repository.MovieRepository
import com.dbesar.ditflix.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Dede Dari Rahmadi on 26/11/24
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _detailState = MutableStateFlow(DetailState())
    val detailState: StateFlow<DetailState> = _detailState.asStateFlow()

    private val _movieId = mutableIntStateOf(0)

    val nowPlayingMovies = repository.getNowPlayingMovies().cachedIn(viewModelScope)
    val topRatedMovies = repository.getTopRatedMovies().cachedIn(viewModelScope)
    val popularMovies = repository.getPopularMovies().cachedIn(viewModelScope)

    fun setMovieId(movieId: Int) {
        _movieId.intValue = movieId
    }

    fun getMovieDetail() {
        viewModelScope.launch {
            _detailState.update {
                it.copy(
                    isLoading = true,
                    movie = null,
                    trailer = emptyList(),
                    error = null
                )
            }
            repository.getMovieDetail(_movieId.intValue).collect { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        //simulate loading
                        delay(1000L)
                        val (movieDetails, trailers) = dataState.data
                        _detailState.update {
                            it.copy(
                                isLoading = false,
                                movie = movieDetails,
                                trailer = trailers
                            )
                        }
                    }

                    is DataState.Error -> {
                        _detailState.update {
                            it.copy(
                                isLoading = false,
                                error = dataState.exception.message
                            )
                        }
                    }
                    else -> {}
                }

            }
        }

    }
}

data class DetailState(
    val isLoading: Boolean = true,
    val movie: MovieDetail? = null,
    val trailer: List<Trailer> = emptyList(),
    val error: String? = null
)