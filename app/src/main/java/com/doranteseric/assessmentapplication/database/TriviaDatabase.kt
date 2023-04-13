package com.doranteseric.assessmentapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.doranteseric.assessmentapplication.dao.TriviaQuestionDao
import com.doranteseric.assessmentapplication.entities.TriviaQuestionEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Database(entities = [TriviaQuestionEntity::class], version = 1, exportSchema = false)
abstract class TriviaDatabase : RoomDatabase() {

    abstract fun triviaQuestionDao(): TriviaQuestionDao

    companion object {
        @Volatile
        private var INSTANCE: TriviaDatabase? = null

        fun getDatabase(context: Context): TriviaDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TriviaDatabase::class.java,
                    "trivia_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}


