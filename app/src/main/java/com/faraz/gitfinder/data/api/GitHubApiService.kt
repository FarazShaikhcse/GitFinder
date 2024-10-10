package com.faraz.gitfinder.data.api

import com.faraz.gitfinder.data.model.Contributor
import com.faraz.gitfinder.data.model.GithubAPIResponse
import com.faraz.gitfinder.data.model.GithubRepository
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApiService {
    // Search repositories by keyword
    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") query: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10,
    ): GithubAPIResponse

    // Get repository details
    @GET("repos/{owner}/{repo}")
    suspend fun getRepositoryDetails(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
    ): GithubRepository

    // Get contributors for a repository
    @GET("repos/{owner}/{repo}/contributors")
    suspend fun getContributors(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
    ): List<Contributor>

    // Get repositories for a specific contributor
    @GET("users/{username}/repos")
    suspend fun getUserRepositories(
        @Path("username") username: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10,
    ): List<GithubRepository>
}
