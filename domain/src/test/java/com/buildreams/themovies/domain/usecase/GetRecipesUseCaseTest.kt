package com.buildreams.themovies.domain.usecase

import app.cash.turbine.test
import com.buildreams.themovies.domain.model.Movie
import com.buildreams.themovies.domain.model.action.Either
import com.buildreams.themovies.domain.model.action.Either.Error
import com.buildreams.themovies.domain.model.action.Either.Success
import com.buildreams.themovies.domain.model.action.error.ErrorEntity
import com.buildreams.themovies.domain.model.action.error.ErrorEntity.EmptyResponseError
import com.buildreams.themovies.domain.model.action.error.ErrorEntity.NetworkError
import com.buildreams.themovies.domain.model.action.error.ErrorEntity.UnknownError
import com.buildreams.themovies.domain.repository.MovieRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetMoviesUseCaseTest {

    @MockK
    lateinit var movieRepository: MovieRepository

    private lateinit var getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        getTopRatedMoviesUseCase = GetTopRatedMoviesUseCase(movieRepository = movieRepository)
    }

    @Test
    fun `Given that the user sees the movies view When request the movies Then return a list of movies`() =
        runTest {
            //given
            coEvery { movieRepository.getTopRatedMovies() } returns flow {
                emit(Success(movies))
            }
            //when
            getTopRatedMoviesUseCase.invoke().test {
                val result: Either<List<Movie>, ErrorEntity> = awaitItem()
                assert(result is Success)
                assertEquals(movies, (result as Success).data)
                awaitComplete()
            }
            //then
            coVerifySequence { movieRepository.getTopRatedMovies() }
        }

    @Test
    fun `Given that the user sees the movies view When request the movies Then return an empty response error`() =
        runTest {
            //given
            coEvery { movieRepository.getTopRatedMovies() } returns flow {
                emit(Error(EmptyResponseError))
            }
            //when
            getTopRatedMoviesUseCase.invoke().test {
                val result: Either<List<Movie>, ErrorEntity> = awaitItem()
                assert(result is Error)
                assertEquals(EmptyResponseError, (result as Error).data)
                awaitComplete()
            }
            //then
            coVerifySequence { movieRepository.getTopRatedMovies() }
        }

    @Test
    fun `Given that the user sees the movies When request the movies Then return a http error`() =
        runTest {
            //given
            val httpError = NetworkError(httpStatus = 400)
            coEvery { movieRepository.getTopRatedMovies() } returns flow {
                emit(Error(httpError))
            }
            //when
            getTopRatedMoviesUseCase.invoke().test {
                val result: Either<List<Movie>, ErrorEntity> = awaitItem()
                assert(result is Error)
                assertEquals(httpError, (result as Error).data)
                awaitComplete()
            }
            //then
            coVerifySequence { movieRepository.getTopRatedMovies() }
        }

    @Test
    fun `Given that the user sees the movies When request the movies Then return a unknown error`() =
        runTest {
            //given
            val unknownError = UnknownError(exception = Exception("something was wrong"))
            coEvery { movieRepository.getTopRatedMovies() } returns flow {
                emit(Error(unknownError))
            }
            //when
            getTopRatedMoviesUseCase.invoke().test {
                val result: Either<List<Movie>, ErrorEntity> = awaitItem()
                assert(result is Error)
                assertEquals(unknownError, (result as Error).data)
                awaitComplete()
            }
            //then
            coVerifySequence { movieRepository.getTopRatedMovies() }
        }

    companion object {
        val movies: List<Movie> = listOf(
            Movie(
                id = 1,
                title = "Schindler's List",
                overview = "Spanning the years 1945 to 1955, a chronicle of the fictional " +
                        "Italian-American Corleone crime family. When organized crime family " +
                        "patriarch, Vito Corleone barely survives an attempt on his life, his " +
                        "youngest son, Michael steps in to take care of the would-be killers, " +
                        "launching a campaign of bloody revenge.",
                voteAverage = 0.0
            ),
            Movie(
                id = 2,
                title = "The Godfather",
                overview = "Spanning the years 1945 to 1955, a chronicle of the fictional " +
                        "Italian-American Corleone crime family. When organized crime family " +
                        "patriarch, Vito Corleone barely survives an attempt on his life, his " +
                        "youngest son, Michael steps in to take care of the would-be killers, " +
                        "launching a campaign of bloody revenge.",
                voteAverage = 0.0
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
    }
}