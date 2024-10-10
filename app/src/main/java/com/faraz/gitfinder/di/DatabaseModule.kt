package com.faraz.gitfinder.di

import android.content.Context
import androidx.room.Room
import com.faraz.gitfinder.data.db.AppDatabase
import com.faraz.gitfinder.data.db.GithubRepositoryEntity
import com.faraz.gitfinder.data.db.RepositoryDao
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
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).fallbackToDestructiveMigration() // Handle migrations
            .build()
    }

    @Provides
    fun provideRepositoryDao(database: AppDatabase): RepositoryDao {
        return database.repositoryDao()
    }
}