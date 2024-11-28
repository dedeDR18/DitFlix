package com.dbesar.ditflix.domain.repository

import androidx.paging.PagingData
import com.dbesar.ditflix.data.remote.model.response.ResponseSearch
import com.dbesar.ditflix.domain.model.Movie
import com.dbesar.ditflix.domain.model.MovieDetail
import com.dbesar.ditflix.domain.model.Trailer
import com.dbesar.ditflix.utils.DataState
import kotlinx.coroutines.flow.Flow

/**
 * Created by Dede Dari Rahmadi on 26/11/24
 */
interface MovieRepository {
    fun getNowPlayingMovies(): Flow<PagingData<Movie>>
    fun getPopularMovies(): Flow<PagingData<Movie>>
    fun getTopRatedMovies(): Flow<PagingData<Movie>>
    suspend fun getMovieDetail(movieId: Int): Flow<DataState<Pair<MovieDetail, List<Trailer>>>>
    suspend fun searchMovie(keyword: String): Flow<DataState<ResponseSearch>>
}