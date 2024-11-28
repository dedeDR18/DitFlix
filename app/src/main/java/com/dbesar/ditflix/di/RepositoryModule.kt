package com.dbesar.ditflix.di

import com.dbesar.ditflix.data.remote.TmdbService
import com.dbesar.ditflix.data.repository.MovieRepositoryImpl
import com.dbesar.ditflix.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Dede Dari Rahmadi on 25/11/24
 */

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepository(
        tmdbService: TmdbService
    ): MovieRepository = MovieRepositoryImpl(tmdbService)

}