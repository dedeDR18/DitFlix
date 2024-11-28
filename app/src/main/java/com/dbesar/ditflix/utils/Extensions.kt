package com.dbesar.ditflix.utils

import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

/**
 * Created by Dede Dari Rahmadi on 28/11/24
 */
object Extensions {
    fun <T : Any> LazyPagingItems<T>.pagingLoadingState(
        isLoaded: (pagingState: Boolean) -> Unit,
    ) {
        this.apply {
            when {
                // data is loading for first time
                loadState.refresh is LoadState.Loading -> {
                    isLoaded(true)
                }
                // data is loading for second time or pagination
                loadState.append is LoadState.Loading -> {
                    isLoaded(true)
                }
                loadState.refresh is LoadState.NotLoading -> {
                    isLoaded(false)
                }
                loadState.append is LoadState.NotLoading -> {
                    isLoaded(false)
                }
            }
        }
    }
}