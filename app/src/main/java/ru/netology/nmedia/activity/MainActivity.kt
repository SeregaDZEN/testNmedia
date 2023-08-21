package ru.netology.nmedia.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.functions.reduceNumber
import ru.netology.nmedia.viewmodel.PostViewModel


class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel  : PostViewModel by viewModels()
        viewModel.data.observe(this) { post ->

            with(binding) {

                author.text = post.author
                published.text = post.published
                content.text = post.content
                textForLike.text = reduceNumber(post.likes)
                textForShare.text = reduceNumber(post.share)
                textForSee.text = post.views.toString()

                buttonLike.setImageResource(if (post.likedByMe) R.drawable.like_red else R.drawable.ic_like)

            }
        }
        binding.buttonLike.setOnClickListener {
            viewModel.like()
        }
        binding.buttonShare.setOnClickListener {
            viewModel.share()
        }
    }

}
