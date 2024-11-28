package com.dbesar.ditflix.data.remote.model.response

import com.dbesar.ditflix.domain.model.Movie
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Dede Dari Rahmadi on 25/11/24
 */

@JsonClass(generateAdapter = true)
data class ResponseMovie(
    @Json(name = "page")
    val page: Int,
    @Json(name = "results")
    val results: List<Movie>,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int
)
