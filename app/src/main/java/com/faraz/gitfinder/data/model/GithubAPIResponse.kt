package com.faraz.gitfinder.data.model

data class GithubAPIResponse(
    val total_count: Int,
    val items: List<GithubRepository>
)
