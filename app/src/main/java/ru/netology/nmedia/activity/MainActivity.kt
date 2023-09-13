package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnClickListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.functions.AndroidUtils
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: PostViewModel by viewModels()

    private val onClickListener = object : OnClickListener {
        override fun onLike(post: Post) {
            viewModel.likeById(post.id)
        }

        override fun onShare(post: Post) {
            viewModel.share(post.id)
        }

        override fun onEdit(post: Post) {
            viewModel.edit(post)
        }

        override fun onRemove(post: Post) {
            viewModel.removeById(post.id)
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
                if (newPost) binding.listRC.smoothScrollToPosition(0)
            }
        }




        viewModel.editedEmptyPost.observe(this) { post ->
            if (post.id != 0L)
                with(binding.changePostText) {
                    requestFocus()
                    setText(post.content)
                    binding.EditView.editCL.visibility = VISIBLE
                    binding.EditView.textEdit.text = post.content
                    binding.EditView.cancelButton.setOnClickListener {

                        binding.EditView.editCL.visibility = GONE
                        clearFocus()
                        setText((""))
                        AndroidUtils.hideKeyboard(this)
                    }
                } else {
                viewModel.editedEmptyPost.observe(this) {
                    binding.changePostText.setOnClickListener {
                        binding.EditViewClear.editCLPost.visibility = VISIBLE
                        binding.EditViewClear.cancelBPost.setOnClickListener {
                            with(binding.changePostText) {
                                binding.EditViewClear.editCLPost.visibility = GONE
                                clearFocus()
                                setText((""))
                                AndroidUtils.hideKeyboard(this)
                            }
                        }
                    }
                }
            }
        }
        binding.changePostText.setOnClickListener{

        }
        binding.saveButton.setOnClickListener {
            with(binding.changePostText) {
                if (text.isNullOrBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        context.getString(R.string.error_empty_context),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    viewModel.changeContent(text.toString())
                    viewModel.save()
                    binding.EditView.editCL.visibility = GONE
                    setText((""))
                    clearFocus()
                    AndroidUtils.hideKeyboard(this)
                }
            }
        }

    }
}

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

