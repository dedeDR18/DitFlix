package com.dbesar.ditflix.data.remote.model.response


import com.dbesar.ditflix.domain.model.Trailer
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseTrailer(
    @Json(name = "id")
    val id: Int,
    @Json(name = "results")
    val results: List<Trailer> = emptyList()
)