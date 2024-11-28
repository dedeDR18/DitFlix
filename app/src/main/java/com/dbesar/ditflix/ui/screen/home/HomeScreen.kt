@file:OptIn(ExperimentalMaterial3Api::class)

package com.dbesar.ditflix.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import com.dbesar.ditflix.R
import com.dbesar.ditflix.domain.model.Movie
import com.dbesar.ditflix.ui.component.BrandText
import com.dbesar.ditflix.ui.component.Category
import com.dbesar.ditflix.ui.component.HorizontalList
import com.dbesar.ditflix.ui.component.Loading
import com.dbesar.ditflix.utils.Extensions.pagingLoadingState

/**
 * Created by Dede Dari Rahmadi on 26/11/24
 */

@Composable
fun HomeScreen(
    onSearchClick: () -> Unit,
    onGetDetail: () -> Unit,
    onItemClick: (movieId: Int) -> Unit,
    nowPlayingMovies: LazyPagingItems<Movie>,
    topRatedMovies: LazyPagingItems<Movie>,
    popularMovies: LazyPagingItems<Movie>,
    detailState: DetailState
) {

    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(showBottomSheet) {
        if (showBottomSheet) {
            onGetDetail.invoke()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    BrandText(stringResource(R.string.ditflix))
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_menu_24),
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { onSearchClick() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search_2_24),
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .padding(
                        start = paddingValues.calculateStartPadding(LayoutDirection.Ltr), // Padding tambahan di sisi start
                        top = paddingValues.calculateTopPadding(),
                        end = paddingValues.calculateEndPadding(LayoutDirection.Ltr),
                        bottom = paddingValues.calculateBottomPadding()
                    )

            ) {
                Category(stringResource(R.string.now_playing))
                HorizontalList(nowPlayingMovies) {
                    onItemClick(it)
                    showBottomSheet = true
                }
                Category(stringResource(R.string.top_rated))
                HorizontalList(topRatedMovies) { movies ->
                    onItemClick(movies)
                    showBottomSheet = true
                }
                Category(stringResource(R.string.popular))
                HorizontalList(popularMovies) { movies ->
                    onItemClick(movies)
                    showBottomSheet = true
                }
                popularMovies.pagingLoadingState {
                    isLoading = it
                }
                Loading(isLoading)

                if (showBottomSheet) {
                    ModalBottomSheet(
                        onDismissRequest = {
                            showBottomSheet = false
                        },
                        sheetState = sheetState
                    ) {
                        // Sheet content
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text("Details", fontSize = 20.sp, fontWeight = FontWeight.Bold)

                            if (detailState.isLoading) {
                                Text(stringResource(R.string.loading))
                            }

                            detailState.error?.let {
                                Text(stringResource(R.string.error, it))
                            }

                            detailState.movie?.let { details ->
                                MovieDetailsSection(details)
                                Text("Trailer : ${if(detailState.trailer.isEmpty()) "not found" else ""}")
                                if (detailState.trailer.isNotEmpty()){
                                    MovieTrailerSection(detailState.trailer)
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}