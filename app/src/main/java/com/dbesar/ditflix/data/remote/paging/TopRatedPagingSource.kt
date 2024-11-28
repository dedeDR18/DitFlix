package com.dbesar.ditflix.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dbesar.ditflix.data.remote.TmdbService
import com.dbesar.ditflix.domain.model.Movie
import retrofit2.HttpException
import javax.inject.Inject

/**
 * Created by Dede Dari Rahmadi on 26/11/24
 */
class TopRatedPagingSource @Inject constructor(
    private val tmdbService: TmdbService
): PagingSource<Int, Movie>(){
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPage = params.key ?: 1
            val topRatedList = tmdbService.fetchTopRatedMovies(page = nextPage)
            LoadResult.Page(
                data = topRatedList.results,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (topRatedList.results.isEmpty()) null else topRatedList.page + 1
            )
        }catch (e: Exception){
            LoadResult.Error(e)
        }catch (eHttp: HttpException){
            LoadResult.Error(eHttp)
        }
    }

}