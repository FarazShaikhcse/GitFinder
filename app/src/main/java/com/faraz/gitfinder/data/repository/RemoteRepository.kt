package com.faraz.gitfinder.data.repository

import com.faraz.gitfinder.data.api.GitHubApiService
import com.faraz.gitfinder.data.db.GithubRepositoryEntity
import com.faraz.gitfinder.data.db.RepositoryDao
import com.faraz.gitfinder.data.model.Contributor
import com.faraz.gitfinder.data.model.GithubAPIResponse
import com.faraz.gitfinder.data.model.Resource
import com.faraz.gitfinder.data.model.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteRepository
    @Inject
    constructor(
        private val apiService: GitHubApiService,
        private val repositoryDao: RepositoryDao,
    ) {
        // method to search repositories
        suspend fun searchRepositories(query: String): GithubAPIResponse? {
            // Use apiService to make API calls
            return try {
                apiService.searchRepositories(query)
            } catch (ex: Exception) {
                null
            }
        }

        fun getRepositories(
            query: String,
            page: Int,
            pageSize: Int,
        ): Flow<Resource<List<GithubRepositoryEntity>>> =
            flow {
                emit(Resource.Loading())

                // Fetch data from the API
                try {
                    val response = apiService.searchRepositories(query, page, pageSize)
                    val repositoryEntities = response.items.map { it.toEntity() }

                    // Save only the first 15 items to the database
                    if (page == 1) {
                        repositoryDao.clearRepositories() // Clear existing entries
                        repositoryDao.insertRepositories(repositoryEntities.take(15))
                    }
                    emit(Resource.Success(repositoryEntities))
                } catch (e: Exception) {
                    // If the API fails, load from the database
                    emit(Resource.Error(e.localizedMessage ?: "An error occurred"))
                }
            }

        suspend fun getSavedRepositories(): Flow<List<GithubRepositoryEntity>> = repositoryDao.getSavedRepositories()

        suspend fun getContributors(
            owner: String,
            repo: String,
        ): List<Contributor> = apiService.getContributors(owner, repo)
    }
