package com.buildreams.themovies.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.buildreams.themovies.domain.model.Movie
import com.buildreams.themovies.util.MovieUtil.imagePathBuilder


@Composable
fun CardsMovies(movies: List<Movie>) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(start = 24.dp, end = 24.dp, top = 12.dp, bottom = 12.dp)
    ) {
        movies.forEach { movie ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                if (movie.backdropPath != null && movie.backdropPath!!.isNotEmpty()) {
                    Image(
                        painter = rememberAsyncImagePainter(imagePathBuilder(movie.backdropPath!!)),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp),
                        alignment = Alignment.CenterStart,
                        contentScale = ContentScale.Crop,
                        alpha = DefaultAlpha,
                        colorFilter = null
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = movie.title,
                        fontSize = 14.sp,
                        fontWeight = Bold,
                        textAlign = TextAlign.Start,
                    )
                    Text(
                        text = movie.voteAverage.toString(),
                        fontSize = 12.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.End,
                    )
                }
                Text(
                    text = movie.overview,
                    fontSize = 12.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.wrapContentHeight()
                )
            }
        }
    }
}