package com.buildreams.themovies.data.repository

import app.cash.turbine.test
import com.buildreams.themovies.data.remote.dto.MovieResponse
import com.buildreams.themovies.data.remote.dto.PageResult
import com.buildreams.themovies.data.source.LocalMovieDataSource
import com.buildreams.themovies.data.source.RemoteMovieDataSource
import com.buildreams.themovies.domain.model.action.Either
import com.buildreams.themovies.domain.model.action.Either.Error
import com.buildreams.themovies.domain.model.action.Either.Success
import com.buildreams.themovies.domain.model.action.error.ErrorEntity.EmptyResponseError
import com.buildreams.themovies.domain.model.action.error.ErrorEntity.UnknownError
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class RemoteMovieRepositoryTest {

    @MockK
    private lateinit var remoteMovieDataSource: RemoteMovieDataSource

    @MockK
    private lateinit var localMovieDataSource: LocalMovieDataSource

    private lateinit var movieRepository: MovieRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        movieRepository = MovieRepository(
            remoteMovieDataSource = remoteMovieDataSource,
            localMovieDataSource = localMovieDataSource
        )
    }

    @Test
    fun `Given a top rated movie request When get movies Then return a list of movies`() =
        runTest {
            //given
            val pageResult = PageResult<MovieResponse?>().apply {
                page = 1
                results = movies
                totalPages = 1
                totalResults = 1
            }
            coEvery { remoteMovieDataSource.getTopRatedMovies() } returns flow {
                emit(Success(pageResult))
            }
            //when
            movieRepository.getTopRatedMovies().test {
                val result: Either = awaitItem()
                assert(result is Success)
                val resultMovies = (result as Success).getData<PageResult<MovieResponse?>>()
                assertNotNull(resultMovies.results)
                assertTrue(resultMovies.results!!.isNotEmpty())
                awaitComplete()
            }
            //then
            coVerifySequence { remoteMovieDataSource.getTopRatedMovies() }
        }

    @Test
    fun `Given a movie request When get movies Then return an empty response error`() =
        runTest {
            //given
            coEvery { remoteMovieDataSource.getTopRatedMovies() } returns flow {
                emit(Error(EmptyResponseError))
            }
            coEvery { localMovieDataSource.getTopRatedMovies() } returns flow {
                emit(Error(EmptyResponseError))
            }
            //when
            movieRepository.getTopRatedMovies().test {
                val result: Either = awaitItem()
                assert(result is Error)
                assertEquals(EmptyResponseError, (result as Error).error)
                awaitComplete()
            }
            //then
            coVerifySequence {
                remoteMovieDataSource.getTopRatedMovies()
                localMovieDataSource.getTopRatedMovies()
            }
        }

    @Test
    fun `Given a recipes request When get recipes Then return a unknown error`() =
        runTest {
            //given
            val exception = UnknownError(Exception())
            coEvery { remoteMovieDataSource.getTopRatedMovies() } returns flow {
                emit(Error(exception))
            }
            coEvery { localMovieDataSource.getTopRatedMovies() } returns flow {
                emit(Error(exception))
            }
            //when
            movieRepository.getTopRatedMovies().test {
                val result: Either = awaitItem()
                assert(result is Error)
                assertEquals(exception, (result as Error).error)
                awaitComplete()
            }
            //then
            coVerifySequence {
                remoteMovieDataSource.getTopRatedMovies()
                localMovieDataSource.getTopRatedMovies()
            }
        }

    companion object {
        val movies: List<MovieResponse> = listOf(
            MovieResponse(
                id = 1,
                title = "Schindler's List",
                overview = "Spanning the years 1945 to 1955, a chronicle of the fictional " +
                        "Italian-American Corleone crime family. When organized crime family " +
                        "patriarch, Vito Corleone barely survives an attempt on his life, his " +
                        "youngest son, Michael steps in to take care of the would-be killers, " +
                        "launching a campaign of bloody revenge.",
                adult = false,
                backdropPath = "",
                homepage = "",
                imdbId = "",
                originalLanguage = "en",
                originalTitle = "Schindler's List",
                posterPath = "",
                releaseDate = "",
                status = "",
                tagline = "",
                video = false
            )
        )
    }
}
