package ru.netology.nmedia.viewmodel2

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepositoryFilesImpl
import ru.netology.nmedia.repository.PostRepositoryInSharedPreferenceImpl


private val emptyPost2 = Post(
    id = 0,
    author = "",
    content = "",
    published = "",
    likes = 0,
    views = 0,
    share = 0,
    likedByMe = false
)

class PostViewModel2(application: Application) : AndroidViewModel(application ) {

    private var repository = PostRepositoryFilesImpl(application)

    val dataOfClassPost = repository.getAll()
    private val editedEmptyPost = MutableLiveData(emptyPost2)
    fun likeById(id: Long) = repository.likeById(id)
    fun share(id: Long) = repository.share(id)
    fun removeById(id: Long) = repository.removeById(id)
    fun save() {
        editedEmptyPost.value?.let {
            repository.save(it)
        }
        editedEmptyPost.value = emptyPost2
    }

    fun edit(post: Post) {
        editedEmptyPost.value = post
    }

    fun cancelEdit() {
        editedEmptyPost.value = emptyPost2
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