package com.doranteseric.assessmentapplication.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trivia_questions")
data class TriviaQuestionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val question: String,
    val correct_answer: String,
)
