package com.doranteseric.assessmentapplication.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.doranteseric.assessmentapplication.entities.TriviaQuestionEntity

@Dao
interface TriviaQuestionDao {
    @Insert
    suspend fun insert(triviaQuestion: TriviaQuestionEntity)

    @Query("SELECT * FROM trivia_questions")
    suspend fun getAll(): List<TriviaQuestionEntity>
}
