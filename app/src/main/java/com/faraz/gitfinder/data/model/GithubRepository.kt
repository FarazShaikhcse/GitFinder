package com.faraz.gitfinder.data.model

import com.faraz.gitfinder.data.db.GithubRepositoryEntity

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

//fun serializeRepository(repo: GithubRepository): String {
//    return Gson().toJson(repo)
//}
//
//fun parseRepository(repoString: String): GithubRepository? {
//    return Gson().fromJson(repoString, GithubRepository::class.java)
//}

fun GithubRepository.toEntity(): GithubRepositoryEntity {
    return GithubRepositoryEntity(
        id = this.id,
        name = this.name,
        fullName = this.full_name,
        owner = this.owner, // Store the Owner object directly using TypeConverter
        description = this.description,
        htmlUrl = this.html_url,
        stargazersCount = this.stargazers_count,
        forksCount = this.forks_count,
        language = this.language,
        createdAt = this.created_at,
        updatedAt = this.updated_at,
        topics = this.topics
    )
}
