package com.doranteseric.assessmentapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.doranteseric.assessmentapplication.dao.TriviaQuestionDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TriviaViewModel @Inject constructor(
    private val triviaRepository: TriviaRepository,
    private val triviaQuestionDao: TriviaQuestionDao
) : ViewModel() {

    // Fetches trivia questions from the repository and caches them in the viewModelScope.
    val triviaQuestions: Flow<PagingData<TriviaQuestion>> = triviaRepository.getTriviaQuestions().cachedIn(viewModelScope)

    // Function to save a question to the local database
    fun saveQuestionToLocalDatabase(triviaQuestion: TriviaQuestion) {
        viewModelScope.launch {
            val triviaQuestionEntity = triviaQuestionToEntity(triviaQuestion)
            triviaQuestionDao.insert(triviaQuestionEntity)
        }
    }

    fun getSavedQuestions(): LiveData<List<TriviaQuestion>> {
        return liveData {
            val list = triviaQuestionDao.getAll()
            val triviaQuestions = list.map { entityToTriviaQuestion(it) }
            emit(triviaQuestions)
        }
    }
}




