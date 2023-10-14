package ru.netology.nmedia.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.dto.Post

class PostRepositoryFilesImpl(private val context: Context) : PostRepository {
    private val gson = Gson()

    //private val prefs = context.getSharedPreferences("repo", Context.MODE_PRIVATE)
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type

    // private val key = "posts"
    private val filename = "posts.json"


    private var nextId = 1L
    private var posts = emptyList<Post>()
        set(value) {
            field = value
            sync() //
        }

    private val livePosts = MutableLiveData(posts)

    override fun getAll() = livePosts

    init {
        val file = context.filesDir.resolve(filename)
        if (file.exists()) {
           context.openFileInput(filename).bufferedReader().use { itStr ->
                posts = gson.fromJson(itStr, type)
                nextId = posts.maxOf { it.id } + 1
                livePosts.value = posts

            }
        }
    }


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
            if (it.id != id) it else it.copy(share = it.share + 1, shareByMe = true)
        }
        livePosts.value = posts


    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        livePosts.value = posts

    }

    override fun save(post: Post) {
        posts = if (post.id == 0L) {
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

    private fun sync() {
        context.openFileOutput(filename, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(posts))
        }
    }
}
