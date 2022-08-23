package com.buildreams.themovies.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.buildreams.themovies.domain.model.Movie
import com.buildreams.themovies.util.MovieUtil.imagePathBuilder

@Composable
fun CardsMovies(movies: List<Movie>) {
    LazyColumn {
        items(movies) { movie ->
            Card(
                border = BorderStroke(1.dp, Color.LightGray),
                modifier = Modifier
                    .height(200.dp)
                    .padding(start = 12.dp, end = 12.dp, top = 4.dp, bottom = 4.dp),
            ) {
                Column(
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
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
                        modifier = Modifier
                            .fillMaxWidth(),
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
                        textAlign = TextAlign.Justify,
                        maxLines = 2,
                        lineHeight = 15.sp,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.wrapContentHeight()
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MoviePreview() {
    CardsMovies(
        listOf(
            Movie(
                id = 1,
                title = "Schindler's List",
                overview = "Spanning the years 1945 to 1955, a chronicle of the fictional " +
                        "Italian-American Corleone crime family. When organized crime family " +
                        "patriarch, Vito Corleone barely survives an attempt on his life, his " +
                        "youngest son, Michael steps in to take care of the would-be killers, " +
                        "launching a campaign of bloody revenge.",
                voteAverage = 0.0,
                backdropPath = "/kGzFbGhp99zva6oZODW5atUtnqi.jpg"
            ),
            Movie(
                id = 2,
                title = "The Godfather",
                overview = "Spanning the years 1945 to 1955, a chronicle of the fictional " +
                        "Italian-American Corleone crime family. When organized crime family " +
                        "patriarch, Vito Corleone barely survives an attempt on his life, his " +
                        "youngest son, Michael steps in to take care of the would-be killers, " +
                        "launching a campaign of bloody revenge.",
                voteAverage = 0.0,
                backdropPath = "/tmU7GeKVybMWFButWEGl2M4GeiP.jpg"
            ),
            Movie(
                id = 3,
                title = "The Green Mile",
                overview = "A supernatural tale set on death row in a Southern prison, where gentle " +
                        "giant John Coffey possesses the mysterious power to heal people's ailments. " +
                        "When the cell block's head guard, Paul Edgecomb, recognizes Coffey's " +
                        "miraculous gift, he tries desperately to help stave off the condemned man's" +
                        " execution.",
                voteAverage = 0.0
            )
        )
    )
}