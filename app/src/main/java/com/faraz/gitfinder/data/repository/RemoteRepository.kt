package com.faraz.gitfinder.data.repository

import com.faraz.gitfinder.data.api.GitHubApiService
import com.faraz.gitfinder.data.model.GithubAPIResponse
import javax.inject.Inject

class RemoteRepository @Inject constructor(private val apiService: GitHubApiService) {

    // Example method to search repositories
    suspend fun searchRepositories(query: String): GithubAPIResponse? {
        // Use apiService to make API calls
        return try {
            apiService.searchRepositories(query)
        } catch (ex: Exception) {
            null
        }
    }
}
