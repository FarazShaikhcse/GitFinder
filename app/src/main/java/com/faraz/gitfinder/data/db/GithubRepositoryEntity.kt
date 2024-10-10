package com.faraz.gitfinder.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.faraz.gitfinder.data.model.Owner

@Entity(tableName = "repositories")
data class GithubRepositoryEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val fullName: String,
    val owner: Owner?,  // Store Owner as a stringified JSON
    val description: String?,
    val htmlUrl: String,
    val stargazersCount: Int,
    val forksCount: Int,
    val language: String?,
    val createdAt: String,
    val updatedAt: String,
    val topics: List<String>? // Using TypeConverter for List<String>
)
