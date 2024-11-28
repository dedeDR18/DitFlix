package com.dbesar.ditflix.utils.route

/**
 * Created by Dede Dari Rahmadi on 26/11/24
 */
sealed class AppRoute(val route: String) {
    data object HomeScreen : AppRoute("homeScreen")
    data object SearchScreen : AppRoute("searchScreen")
}