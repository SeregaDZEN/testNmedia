package ru.netology.nmedia.dto

data class Post(

    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likes: Int = 0,
    val views: Int = 0,
    val share: Int = 0,
    val likedByMe: Boolean = false,
    val shareByMe: Boolean = false,
    val uRl : String? = "https://kinescope.io/7128cdca-d31e-4a6e-9ac5-c1f896546600"
)
