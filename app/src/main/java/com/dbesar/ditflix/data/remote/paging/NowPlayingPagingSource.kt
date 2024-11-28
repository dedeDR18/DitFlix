package com.dbesar.ditflix.data.remote.paging


import android.util.Log
import androidx.datastore.core.IOException
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dbesar.ditflix.data.remote.TmdbService
import com.dbesar.ditflix.domain.model.Movie
import retrofit2.HttpException
import javax.inject.Inject

/**
 * Created by Dede Dari Rahmadi on 26/11/24
 */
class NowPlayingPagingSource @Inject constructor(
    private val tmdbService: TmdbService
) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPage = params.key ?: 1
            val nowPlayingList = tmdbService.fetchNowPlayingMovies(page = nextPage)
            LoadResult.Page(
                data = nowPlayingList.results,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (nowPlayingList.results.isEmpty()) null else nowPlayingList.page + 1
            )
        } catch (e: IOException) {
            Log.d("exception", e.message.toString())
            return LoadResult.Error(e)
        } catch (httpE: HttpException) {
            Log.d("http exception", httpE.message.toString())
            return LoadResult.Error(httpE)
        }
    }
}