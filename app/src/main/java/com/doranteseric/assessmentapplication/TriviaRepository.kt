package com.doranteseric.assessmentapplication

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.doranteseric.assessmentapplication.TriviaApi
import com.doranteseric.assessmentapplication.TriviaPagingSource
import com.doranteseric.assessmentapplication.TriviaQuestion
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// TriviaRepository is responsible for providing the data from the API to the ViewModel.
class TriviaRepository @Inject constructor(private val triviaApi: TriviaApi) {

    // Fetches trivia questions from the API using the TriviaPagingSource and returns them as a Flow of PagingData.
    fun getTriviaQuestions(): Flow<PagingData<TriviaQuestion>> {
        return Pager(PagingConfig(pageSize = 10)) {
            TriviaPagingSource(triviaApi)
        }.flow
    }
}
