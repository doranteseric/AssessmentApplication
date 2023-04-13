package com.doranteseric.assessmentapplication

import com.doranteseric.assessmentapplication.entities.TriviaQuestionEntity

fun triviaQuestionToEntity(triviaQuestion: TriviaQuestion): TriviaQuestionEntity {
    return TriviaQuestionEntity(
        question = triviaQuestion.question,
        correct_answer = triviaQuestion.correct_answer
    )
}

fun entityToTriviaQuestion(entity: TriviaQuestionEntity): TriviaQuestion {
    return TriviaQuestion(
        category = "_",
        type = "_",
        difficulty = "_",
        question = entity.question,
        correct_answer = entity.correct_answer,
        incorrect_answers = emptyList()
    )
}