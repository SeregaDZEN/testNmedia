package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnClickListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post

import ru.netology.nmedia.viewmodel.PostViewModel
import ru.netology.nmedia.viewmodel2.PostViewModel2


class MainActivity : AppCompatActivity() {

    val newPostLauncher = registerForActivityResult(NewPostResultContract) { text ->
        text ?: return@registerForActivityResult
        viewModel.changeContent(text)
        viewModel.save()
    }

    private val viewModel: PostViewModel2 by viewModels() // тут заменил на 2 постVM, можно вернуть 1

    private val onClickListener = object : OnClickListener {

        override fun onEdit(post: Post) {
            newPostLauncher.launch(post.content)
            viewModel.edit(post)
        }

        override fun onLike(post: Post) {
            viewModel.likeById(post.id)
        }

        override fun onShare(post: Post) {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, post.content)
            }
            val chooser = Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(chooser)
        }

        override fun onRemove(post: Post) {
            viewModel.removeById(post.id)
        }

        override fun playVideo(post: Post) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.uRl))

            val chooser = Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(chooser)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val adapter = PostsAdapter(onClickListener)
        binding.listRC.adapter = adapter
        viewModel.dataOfClassPost.observe(this) { posts ->
            val newPost = posts.size > adapter.currentList.size
            adapter.submitList(posts) {
                if (newPost) binding.listRC.smoothScrollToPosition(0)  // поднять скрол вверх
            }
        }

        binding.plus.setOnClickListener {
            newPostLauncher.launch(null)
        }

    }
}


//binding.EditView.cancelButton.setOnClickListener {
//    binding.EditView.editCL.visibility = GONE
//    with(binding.changePostText) {
//        viewModel.cancelEdit()
//        clearFocus()
//        setText("")
//        AndroidUtils.hideKeyboard(this)
//    }
//}
//
//binding.EditViewClear.cancelBPost.setOnClickListener {
//    binding.EditViewClear.editCLPost.visibility = GONE
//    with(binding.changePostText) {
//        clearFocus()
//        setText("")
//        AndroidUtils.hideKeyboard(this)
//    }
//}
//
//binding.changePostText.setOnFocusChangeListener { _, active ->
//    if (active && viewModel.editedEmptyPost.value?.id == 0L) {
//        binding.EditViewClear.editCLPost.visibility = VISIBLE
//    }
//}
//
//viewModel.editedEmptyPost.observe(this) { post ->
//    if (post.id != 0L) {
//        with(binding.changePostText) {
//            requestFocus()
//            setText(post.content)
//            binding.EditView.editCL.visibility = VISIBLE
//            binding.EditView.textEdit.text = post.content
//        }
//    }
//}
//
//binding.saveButton.setOnClickListener {
//    with(binding.changePostText) {
//        if (text.isNullOrBlank()) {
//            Toast.makeText(
//                this@MainActivity,
//                context.getString(R.string.error_empty_context),
//                Toast.LENGTH_SHORT
//            ).show()
//        } else {
//            viewModel.changeContent(text.toString())
//            viewModel.save()
//            binding.EditViewClear.editCLPost.visibility = GONE
//            binding.EditView.editCL.visibility = GONE
//            setText((""))
//            clearFocus()
//            AndroidUtils.hideKeyboard(this)
//        }
//    }
//}

//  binding.EditViewClear.editCLPost.visibility = VISIBLE

//binding.changePostText.setOnFocusChangeListener { view, b ->
//    if (b) {
//        if (binding.changePostText.text.isNullOrBlank()){
//
//            binding.EditViewClear.editCLPost.visibility = VISIBLE
//            binding.EditView.editCL.visibility = GONE
//        } else{
//            binding.EditViewClear.editCLPost.visibility = GONE
//            binding.EditView.editCL.visibility = VISIBLE
//        }
//    } else {
//        binding.EditViewClear.editCLPost.visibility = GONE
//        binding.EditView.editCL.visibility = GONE
//    }
//}


//else {
//    with(binding.changePostText) {
//        setOnClickListener {
//
//
//            binding.EditViewClear.editCLPost.visibility = android.view.View.VISIBLE
//            binding.EditViewClear.cancelBPost.setOnClickListener {
//
//                binding.EditViewClear.editCLPost.visibility = android.view.View.GONE
//                clearFocus()
//                setText((""))
//
//            }
//        }
//    }
//}

//
//        binding.saveButton.setOnClickListener {
//            with(binding.changePostText) {
//                if (text.isNullOrBlank()) {
//                    Toast.makeText(
//                        this@MainActivity,
//                        context.getString(R.string.error_empty_context),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                } else {
//                    viewModel.changeContent(text.toString())
//                    viewModel.save()
//                    binding.EditViewClear.editCLPost.visibility = GONE
//                    binding.EditView.editCL.visibility = GONE
//                    setText((""))
//                    clearFocus()
//                    AndroidUtils.hideKeyboard(this)
//                }
//            }
//        }

