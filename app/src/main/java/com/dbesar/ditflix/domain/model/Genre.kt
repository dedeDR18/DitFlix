package com.dbesar.ditflix.domain.model

import com.squareup.moshi.Json

/**
 * Created by Dede Dari Rahmadi on 26/11/24
 */
data class Genre(
    @field:Json( name = "id")
    val id: Int?,
    @field:Json( name = "name")
    val name: String
)
