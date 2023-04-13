package com.doranteseric.assessmentapplication

import androidx.paging.PagingSource
import androidx.paging.PagingState

// TriviaPagingSource is responsible for fetching data from the API and providing paging functionality.
class TriviaPagingSource(private val triviaApi: TriviaApi) : PagingSource<Int, TriviaQuestion>() {
    // Fetches data and returns it as a LoadResult.
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TriviaQuestion> {
        return try {
            val nextPage = params.key ?: 1
            val response = triviaApi.getTriviaQuestions()
            LoadResult.Page(
                data = response.results,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = nextPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    // Determines the refresh key for the PagingSource.
    override fun getRefreshKey(state: PagingState<Int, TriviaQuestion>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}
