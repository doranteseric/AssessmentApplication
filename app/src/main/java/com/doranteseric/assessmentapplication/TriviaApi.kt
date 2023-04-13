package com.doranteseric.assessmentapplication

import retrofit2.http.GET

// TriviaApi is the interface for our API calls, using Retrofit.
interface TriviaApi {
    // Fetches trivia questions from the API.
    @GET("api.php?amount=10")//&type=multiple for multiple choice questions
    suspend fun getTriviaQuestions(): ApiResponse
}
