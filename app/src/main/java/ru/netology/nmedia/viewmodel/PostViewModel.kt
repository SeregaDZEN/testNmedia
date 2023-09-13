package ru.netology.nmedia.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl

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

class PostViewModel : ViewModel() {

    private var repository = PostRepositoryInMemoryImpl()

    val dataOfClassPost = repository.getAll()
    val editedEmptyPost = MutableLiveData(emptyPost)
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

    fun changeContent(content: String) {
        editedEmptyPost.value?.let {
            val text = content.trim()

            if (it.content != text) {
                editedEmptyPost.value = it.copy(content = text)
            }
        }
    }

}