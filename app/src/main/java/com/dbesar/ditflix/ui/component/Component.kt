package com.dbesar.ditflix.ui.component

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.paging.compose.LazyPagingItems
import coil3.compose.AsyncImage
import com.dbesar.ditflix.R
import com.dbesar.ditflix.domain.model.Movie
import com.dbesar.ditflix.utils.Constants
import com.dbesar.ditflix.utils.customFont

/**
 * Created by Dede Dari Rahmadi on 26/11/24
 */

@Composable
fun BrandText(
    text: String
) {
    Text(
        fontSize = 32.sp,
        fontFamily = customFont,
        text = text,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun Poster(path: String) {
    Card(
        modifier = Modifier
            .padding(
                start = 4.dp,
                end = 4.dp
            ),
        elevation = CardDefaults.cardElevation()
    ) {
        AsyncImage(
            model = Constants.IMAGE_URL.plus(path),
            contentDescription = "image"
        )
    }
}

@Composable
fun Title(title: String) {
    Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
}

@Composable
fun Category(category: String) {
    Text(
        text = category,
        modifier = Modifier.padding(start = 16.dp),
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    )
}

@Composable
fun Overview(overview: String) {
    Text(
        text = overview,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Justify
    )
}

@Composable
fun SearchItem(movie: Movie) {
    Card(
        elevation = CardDefaults.cardElevation()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Poster(movie.posterPath ?: "")
            Title(title = movie.title)
        }
    }
}

@Composable
fun MovieItem(movie: Movie, onItemClick: (movieId: Int) -> Unit) {
    Card(
        modifier = Modifier
            .clickable {
                onItemClick(movie.id)
            }
            .padding(4.dp),
        elevation = CardDefaults.cardElevation()
    ) {
        AsyncImage(
            model = Constants.IMAGE_URL.plus(movie.posterPath),
            contentDescription = "image"
        )
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun YouTubePlayer(videoId: String? = "gdPI9gDq_qg") {
    val youtubeUrl = "https://www.youtube.com/embed/".plus(videoId)

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
            .aspectRatio(16 / 9f),
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()
                webChromeClient = WebChromeClient()
                setLayerType(WebView.LAYER_TYPE_HARDWARE, null)
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                loadUrl(youtubeUrl)
            }
        }
    )
}

@Composable
fun HorizontalList(list: LazyPagingItems<Movie>, onItemClick: (movieId: Int) -> Unit){
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 8.dp,
                bottom = 8.dp
            ),
        content = {
            items(list.itemCount) { i ->
                MovieItem(
                    list[i]!!,
                    onItemClick = { movieId ->
                        onItemClick(movieId)
                    }
                )
            }
        }
    )
}

@Composable
fun Loading(visibility: Boolean) {
    if (visibility){
        Text(stringResource(R.string.loading), modifier = Modifier.padding(start = 16.dp), fontSize = 20.sp)
    }
}