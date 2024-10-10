package com.faraz.gitfinder.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RepositoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepositories(repositories: List<GithubRepositoryEntity>)

    @Query("SELECT * FROM repositories LIMIT 15")
    fun getSavedRepositories(): Flow<List<GithubRepositoryEntity>>

    @Query("DELETE FROM repositories")
    suspend fun clearRepositories()
}
