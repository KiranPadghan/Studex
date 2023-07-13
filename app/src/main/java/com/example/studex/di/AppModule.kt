package com.example.studex.di

import android.content.Context
import androidx.room.Room
import com.example.studex.modules.repository.UserRepository
import com.example.studex.roomDb.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Provides
    @Singleton
    fun provideMemesDatabase(@ApplicationContext context: Context): UserDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            UserDatabase::class.java,
            UserDatabase.USER_DATABASE
        )
            .fallbackToDestructiveMigration()
            .build()
    }

//    @Provides
//    fun provideMemesDao(db: UserDatabase): MemesDao {
//        return db.memesDao
//    }

    @Provides
    @Singleton
    fun provideHomeRepository( userDatabase: UserDatabase): UserRepository {
        return UserRepository(userDatabase)
    }

    @Provides
    fun provideCoroutineExceptionHandler(): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, exception ->
            println("CoroutineException -> ${exception.printStackTrace()}")
        }
    }
}