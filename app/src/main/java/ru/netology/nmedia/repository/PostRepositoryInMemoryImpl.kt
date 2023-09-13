package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemoryImpl : PostRepository {
    private var nextId = 1L
    private var posts = listOf(
        Post(
            id = 44, "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "13 августа в 19:36",
            likes = 0,
            share = 0,
            views = 0,
            likedByMe = false
        ),
        Post(
            id = 22, "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "13 августа в 19:36",
            likes = 0,
            share = 0,
            views = 0,
            likedByMe = false
        ),
        Post(
            id = 33, "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "13 августа в 19:36",
            likes = 0,
            share = 0,
            views = 0,
            likedByMe = false
        ),
        Post(
            id = 47, "Нетология. Университет интернет-профессий будущего",
            content = "Два слова",
            published = "13 августа в 19:36",
            likes = 0,
            share = 0,
            views = 0,
            likedByMe = false
        ),

        )
    private val livePosts = MutableLiveData(posts)
    override fun getAll() = livePosts


    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                likedByMe = !it.likedByMe,
                likes = if (it.likedByMe) it.likes - 1 else it.likes + 1
            )

        }
        livePosts.value = posts
    }

    override fun share(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(share = it.share + 1)
        }
        livePosts.value = posts

    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        livePosts.value = posts
    }

    override fun save(post: Post) {
        posts = if (post.id == 0L ) {
            listOf(
                post.copy(
                    id = nextId++,
                    author = "Serёga",
                    published = "Now time"
                )
            ) + posts

        } else posts.map {
            if (it.id != post.id) it else it.copy(content = post.content)
        }
        livePosts.value = posts
    }

}
