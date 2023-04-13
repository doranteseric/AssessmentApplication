package com.doranteseric.assessmentapplication

import android.content.Context
import androidx.room.Room
import com.doranteseric.assessmentapplication.database.TriviaDatabase
import com.doranteseric.assessmentapplication.dao.TriviaQuestionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideTriviaDatabase(@ApplicationContext context: Context): TriviaDatabase {
        return Room.databaseBuilder(
            context,
            TriviaDatabase::class.java,
            "trivia_database"
        ).build()
    }

    @Provides
    fun provideTriviaQuestionDao(triviaDatabase: TriviaDatabase): TriviaQuestionDao {
        return triviaDatabase.triviaQuestionDao()
    }
}
