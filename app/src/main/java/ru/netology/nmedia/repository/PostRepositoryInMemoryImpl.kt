package ru.netology.nmedia.repository

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemoryImpl : PostRepository {

    private var post = Post(
        id = 1, "Нетология. Университет интернет-профессий будущего",
        content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
        published = "13 августа в 19:36",
        likes = 0,
        share = 0,
        views = 0,
        likedByMe = false

    )
    private val data = MutableLiveData(post)
    override fun get() = data


    override fun like() {
        post = post.copy(
            likedByMe = !post.likedByMe,
            likes = if (post.likedByMe) post.likes - 1 else post.likes + 1
        )
        data.value = post
    }

    override fun share() {
        post = post.copy(share = post.share + 1)
        data.value = post
    }
}