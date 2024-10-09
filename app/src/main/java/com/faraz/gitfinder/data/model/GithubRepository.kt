package com.faraz.gitfinder.data.model

data class GithubRepository(
val id: Long,
val name: String,
val full_name: String,
val owner: Owner,
val description: String?,
val html_url: String,
val stargazers_count: Int,
val forks_count: Int,
val language: String?,
val created_at: String,
val updated_at: String,
val topics: List<String>?
)

data class Owner(
    val login: String,
    val id: Long,
    val avatar_url: String,
    val html_url: String
)

