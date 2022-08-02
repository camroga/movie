package com.buildreams.themovies.thirdparty

import com.buildreams.themovies.data.remote.MovieRestApi
import com.buildreams.themovies.data.source.RemoteMovieDataSource
import com.doepiccoding.usecase_di.BuildConfig.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
//            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieRestApi(retrofit: Retrofit): MovieRestApi {
        return retrofit.create(MovieRestApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteMovieDataSource(movieRestApi: MovieRestApi): RemoteMovieDataSource {
        return RemoteMovieDataSource(movieRestApi)
    }
}
