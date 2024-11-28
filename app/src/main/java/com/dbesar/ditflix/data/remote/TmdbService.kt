package com.dbesar.ditflix.data.remote

import com.dbesar.ditflix.domain.model.MovieDetail
import com.dbesar.ditflix.data.remote.model.response.ResponseMovie
import com.dbesar.ditflix.data.remote.model.response.ResponseSearch
import com.dbesar.ditflix.data.remote.model.response.ResponseTrailer
import com.dbesar.ditflix.utils.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Dede Dari Rahmadi on 25/11/24
 */
interface TmdbService {
    @GET("movie/now_playing")
    suspend fun fetchNowPlayingMovies(
        @Query("page") page: Int,
        @Query("language") lang: String = "en-US",
        @Query("api_key") apiKey: String = API_KEY,
    ): ResponseMovie

    @GET("movie/popular")
    suspend fun fetchPopularMovies(
        @Query("page") page: Int,
        @Query("language") lang: String = "en-US",
        @Query("api_key") apiKey: String = API_KEY,
    ): ResponseMovie

    @GET("movie/top_rated")
    suspend fun fetchTopRatedMovies(
        @Query("page") page: Int,
        @Query("language") lang: String = "en-US",
        @Query("api_key") apiKey: String = API_KEY,
    ): ResponseMovie

    @GET("movie/{movieId}")
    suspend fun movieDetail(
        @Path("movieId") movieId: Int,
        @Query("language") lang: String = "en-US",
        @Query("api_key") apiKey: String = API_KEY,
    ): MovieDetail

    @GET("search/movie?page=1&include_adult=false")
    suspend fun searchMovie(
        @Query("query") searchKey: String,
        @Query("language") lang: String = "en-US",
        @Query("api_key") apiKey: String = API_KEY,
    ): ResponseSearch

    @GET("movie/{movieId}/videos")
    suspend fun fetchMovieTrailer(
        @Path("movieId") movieId: Int,
        @Query("language") lang: String = "en-US",
        @Query("api_key") apiKey: String = API_KEY,
    ): ResponseTrailer
}