package com.faraz.gitfinder.data.model

data class Contributor(
    val login: String,
    val id: Long,
    val contributions: Int,
    val avatar_url: String,
    val html_url: String
)
