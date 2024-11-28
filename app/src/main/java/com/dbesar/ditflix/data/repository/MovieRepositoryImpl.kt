package com.dbesar.ditflix.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dbesar.ditflix.data.remote.TmdbService
import com.dbesar.ditflix.data.remote.model.response.ResponseSearch
import com.dbesar.ditflix.data.remote.paging.NowPlayingPagingSource
import com.dbesar.ditflix.data.remote.paging.PopularPagingSource
import com.dbesar.ditflix.data.remote.paging.TopRatedPagingSource
import com.dbesar.ditflix.domain.model.Movie
import com.dbesar.ditflix.domain.model.MovieDetail
import com.dbesar.ditflix.domain.model.Trailer
import com.dbesar.ditflix.domain.repository.MovieRepository
import com.dbesar.ditflix.utils.Constants
import com.dbesar.ditflix.utils.DataState
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by Dede Dari Rahmadi on 26/11/24
 */

class MovieRepositoryImpl(
    private val tmdbService: TmdbService
) : MovieRepository {

    override fun getNowPlayingMovies(): Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(pageSize = Constants.PAGE_SIZE),
        pagingSourceFactory = { NowPlayingPagingSource(tmdbService) }
    ).flow

    override fun getPopularMovies(): Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(pageSize = Constants.PAGE_SIZE),
        pagingSourceFactory = { PopularPagingSource(tmdbService) }
    ).flow

    override fun getTopRatedMovies(): Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(pageSize = Constants.PAGE_SIZE),
        pagingSourceFactory = { TopRatedPagingSource(tmdbService) }
    ).flow

    override suspend fun getMovieDetail(movieId: Int): Flow<DataState<Pair<MovieDetail, List<Trailer>>>> = flow {
        emit(DataState.Loading)
        try {
            //dengan menggunakan scope ini jika salah satu api gagal, makan akan dibatalkan semua
            coroutineScope {
                val movieDetailDeffered = async { tmdbService.movieDetail(movieId) }
                val movieTrailerDeffered = async { tmdbService.fetchMovieTrailer(movieId) }
                emit(DataState.Success(movieDetailDeffered.await() to movieTrailerDeffered.await().results))
            }
        } catch (e: Exception){
            emit(DataState.Error(e))
        }
    }

    override suspend fun searchMovie(keyword: String): Flow<DataState<ResponseSearch>> = flow {
        emit(DataState.Loading)
        try {
            val searchResult = tmdbService.searchMovie(keyword)
            emit(DataState.Success(searchResult))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}