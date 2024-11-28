package com.dbesar.ditflix.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dbesar.ditflix.domain.model.MovieDetail
import com.dbesar.ditflix.domain.model.Trailer
import com.dbesar.ditflix.ui.component.Overview
import com.dbesar.ditflix.ui.component.Poster
import com.dbesar.ditflix.ui.component.Title
import com.dbesar.ditflix.ui.component.YouTubePlayer

/**
 * Created by Dede Dari Rahmadi on 27/11/24
 */
@Composable
fun MovieDetailsSection(movie: MovieDetail){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 16.dp
            )
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Poster(movie.posterPath)
        Title(movie.title)
        Overview(movie.overview)
    }
}

@Composable
fun MovieTrailerSection(trailes: List<Trailer>){
    YouTubePlayer(trailes[0].key)
}