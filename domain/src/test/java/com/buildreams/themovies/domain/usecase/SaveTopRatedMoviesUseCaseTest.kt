package com.buildreams.themovies.domain.usecase

import app.cash.turbine.test
import com.buildreams.themovies.domain.model.Movie
import com.buildreams.themovies.domain.model.action.Either
import com.buildreams.themovies.domain.model.action.Either.Error
import com.buildreams.themovies.domain.model.action.Either.Success
import com.buildreams.themovies.domain.model.action.error.ErrorEntity
import com.buildreams.themovies.domain.model.action.error.ErrorEntity.DataBaseError
import com.buildreams.themovies.domain.repository.MovieRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SaveTopRatedMoviesUseCaseTest {

    @MockK
    lateinit var movieRepository: MovieRepository

    private lateinit var saveTopRatedMoviesUseCase: SaveTopRatedMoviesUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        saveTopRatedMoviesUseCase = SaveTopRatedMoviesUseCase(movieRepository = movieRepository)
    }


    @Test
    fun `Given that the user load the remote recipes When the recipes are available Then save the recipe in the data base`() =
        runTest {
            //given
            coEvery { movieRepository.insertAllTopRatedMovies(any()) } returns flow {
                emit(Success(true))
            }
            //when
            saveTopRatedMoviesUseCase.invoke(recipes = movies).test {
                val result: Either<Boolean, ErrorEntity> = awaitItem()
                assert(result is Success)
                assertTrue((result as Success).data)
                awaitComplete()
            }
            //then
            coVerifySequence { movieRepository.insertAllTopRatedMovies(movies = movies) }
        }

    @Test
    fun `Given that the user load the remote recipes When the recipes are available Then return an error`() =
        runTest {
            //given
            coEvery { movieRepository.insertAllTopRatedMovies(movies = movies) } returns flow {
                emit(Error(DataBaseError))
            }
            //when
            saveTopRatedMoviesUseCase.invoke(recipes = movies).test {
                val result: Either<Boolean, ErrorEntity> = awaitItem()
                assert(result is Error)
                assertEquals(DataBaseError, (result as Error).data)
                awaitComplete()
            }
            //then
            coVerifySequence { movieRepository.insertAllTopRatedMovies(movies = movies) }
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