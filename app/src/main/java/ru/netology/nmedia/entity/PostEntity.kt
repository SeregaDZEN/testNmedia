package ru.netology.nmedia.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.dto.Post


@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likes: Int = 0,
    val views: Int = 0,
    val share: Int = 0,
    val likedByMe: Boolean = false,
    val shareByMe: Boolean = false,
    val uRl: String? = "https://kinescope.io/7128cdca-d31e-4a6e-9ac5-c1f896546600"
) {
    fun toDto(): Post = Post(
        id = id,
        author = author,
        content = content,
        published = published,
        likes = likes,
        views = views,
        share = share,
        likedByMe = likedByMe,
        shareByMe = shareByMe,
        uRl = uRl
    )
    companion object{
        fun fromDto(dto :Post) : PostEntity = with(dto){
            PostEntity(
                id = id,
                author = author,
                content = content,
                published = published,
                likes = likes,
                views = views,
                share = share,
                likedByMe = likedByMe,
                shareByMe = shareByMe,
                uRl = uRl
            )
        }
    }
}
