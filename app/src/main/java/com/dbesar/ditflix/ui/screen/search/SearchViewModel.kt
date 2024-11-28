package com.dbesar.ditflix.ui.screen.search

import androidx.compose.animation.fadeIn
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dbesar.ditflix.domain.model.Movie
import com.dbesar.ditflix.domain.repository.MovieRepository
import com.dbesar.ditflix.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Dede Dari Rahmadi on 28/11/24
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _searchState = MutableStateFlow(SearchState())
    val searchState: StateFlow<SearchState> = _searchState.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun searchMovie(query: String) {
        viewModelScope.launch {
            flowOf(query).debounce(300)
                .filter {
                    it.trim().isEmpty().not()
                }
                .distinctUntilChanged()
                .flatMapLatest {
                    repository.searchMovie(query)
                }.collect { dataState ->
                    when (dataState) {
                        is DataState.Success -> {
                            _searchState.update {
                                it.copy(
                                    isLoading = false,
                                    movies = dataState.data.results
                                )
                            }
                        }

                        is DataState.Error -> {
                            _searchState.update {
                                it.copy(
                                    isLoading = false,
                                    error = dataState.exception.message,
                                    movies = emptyList()
                                )
                            }
                        }

                        DataState.Loading -> {
                            _searchState.update {
                                it.copy(
                                    isLoading = true,
                                    error = null,
                                    movies = emptyList()
                                )
                            }
                        }
                    }
                }
        }
    }
}

data class SearchState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val movies: List<Movie> = emptyList()
)