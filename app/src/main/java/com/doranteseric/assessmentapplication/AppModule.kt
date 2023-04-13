package com.doranteseric.assessmentapplication

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// AppModule provides dependencies for application, including the TriviaApi and TriviaRepository.
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    // Provides the TriviaApi instance using Retrofit.
    @Singleton
    @Provides
    fun provideTriviaApi(): TriviaApi = Retrofit.Builder()
        .baseUrl("https://opentdb.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(TriviaApi::class.java)

    // Provides the TriviaRepository instance, injecting the TriviaApi dependency.
    @Singleton
    @Provides
    fun provideTriviaRepository(triviaApi: TriviaApi): TriviaRepository = TriviaRepository(triviaApi)
}

