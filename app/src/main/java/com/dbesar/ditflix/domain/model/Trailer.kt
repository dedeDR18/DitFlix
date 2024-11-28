package com.dbesar.ditflix.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Dede Dari Rahmadi on 27/11/24
 */
@JsonClass(generateAdapter = true)
data class Trailer(
    @Json(name = "id")
    val id: String?,
    @Json(name = "key")
    val key: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "official")
    val official: Boolean?,
    @Json(name = "published_at")
    val publishedAt: String?,
    @Json(name = "site")
    val site: String?,
    @Json(name = "size")
    val size: Int?,
    @Json(name = "type")
    val type: String?
)
