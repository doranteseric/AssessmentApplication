package com.doranteseric.assessmentapplication

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// ApiResponse is the data class representing the API response.
data class ApiResponse(val response_code: Int, val results: List<TriviaQuestion>)

// TriviaQuestion is the data class representing a single trivia question.
@kotlinx.serialization.Serializable
data class TriviaQuestion(
    val category: String,
    val type: String,
    val difficulty: String,
    val question: String,
    val correct_answer: String,
    val incorrect_answers: List<String>
)
