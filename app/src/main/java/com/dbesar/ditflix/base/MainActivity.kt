package com.dbesar.ditflix.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.dbesar.ditflix.ui.screen.home.HomeScreen
import com.dbesar.ditflix.ui.screen.home.HomeViewModel
import com.dbesar.ditflix.ui.screen.search.SearchScreen
import com.dbesar.ditflix.ui.screen.search.SearchViewModel
import com.dbesar.ditflix.ui.theme.DitFlixTheme
import com.dbesar.ditflix.utils.route.AppRoute
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DitFlixTheme {
                DitflixApp()
            }
        }
    }
}


@Composable
fun DitflixApp() {
    val navController = rememberNavController()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        NavHost(
            navController = navController,
            startDestination = AppRoute.HomeScreen.route,
            modifier = Modifier.fillMaxSize()
        ) {
            composable(AppRoute.HomeScreen.route) {
                val viewModel: HomeViewModel = hiltViewModel()
                val nowPlayingMovies = viewModel.nowPlayingMovies.collectAsLazyPagingItems()
                val topRatedMovies = viewModel.topRatedMovies.collectAsLazyPagingItems()
                val popularMovies = viewModel.popularMovies.collectAsLazyPagingItems()
                val detailState by viewModel.detailState.collectAsState()
                HomeScreen(
                    onSearchClick = {
                        navigateToTop(navController, AppRoute.SearchScreen.route)
                    },
                    onGetDetail = {
                        viewModel.getMovieDetail()
                    },
                    onItemClick = { movieId ->
                        viewModel.setMovieId(movieId)
                    },
                    nowPlayingMovies = nowPlayingMovies,
                    topRatedMovies = topRatedMovies,
                    popularMovies = popularMovies,
                    detailState = detailState
                )
            }
            composable(AppRoute.SearchScreen.route) {
                val viewModel: SearchViewModel = hiltViewModel()
                val searchState by viewModel.searchState.collectAsState()
                SearchScreen(
                    searchState = searchState,
                    onSearchClick = { query ->
                        viewModel.searchMovie(query)
                    }
                )
            }
        }
    }
}

private fun navigateToTop(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { homeScreen ->
            popUpTo(homeScreen) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DitFlixTheme {

    }
}