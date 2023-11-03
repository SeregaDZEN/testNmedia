package ru.netology.nmedia.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositorySQLiteImpl

private val emptyPost = Post(
    id = 0,
    author = "",
    content = "",
    published = "",
    likes = 0,
    views = 0,
    share = 0,
    likedByMe = false
)

class PostViewModel(application: Application) : AndroidViewModel(application ) {
  //  val c = AppDb
   // AppDb

    private val repository: PostRepository = PostRepositorySQLiteImpl(

        AppDb.getInstance(application).postDao

    )

    val dataOfClassPost = repository.getAll()
    private val editedEmptyPost = MutableLiveData(emptyPost)
    fun likeById(id: Long) = repository.likeById(id)
    fun share(id: Long) = repository.share(id)
    fun removeById(id: Long) = repository.removeById(id)
    fun save() {
        editedEmptyPost.value?.let {
            repository.save(it)
        }
        editedEmptyPost.value = emptyPost
    }

    fun edit(post: Post) {
        editedEmptyPost.value = post
    }

    fun cancelEdit() {
        editedEmptyPost.value = emptyPost
    }

    fun changeContent(content: String) {
        editedEmptyPost.value?.let {
            val text = content.trim()

            if (it.content != text) {
                editedEmptyPost.value = it.copy(content = text)
            }
        }
    }

}