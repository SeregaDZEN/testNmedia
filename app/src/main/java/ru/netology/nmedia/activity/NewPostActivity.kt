package ru.netology.nmedia.activity


import android.content.Context
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE

import ru.netology.nmedia.databinding.NewPostActivityBinding


class NewPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = NewPostActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val text = intent?.getStringExtra(Intent.EXTRA_TEXT)
        binding.contentNewPost.setText(text)

        binding.add.setOnClickListener {
            val text = binding.contentNewPost.text.toString()
            if (text.isBlank()) {
                setResult(RESULT_CANCELED)
            } else {
                setResult(RESULT_OK, Intent().apply { putExtra(Intent.EXTRA_TEXT, text) })
            }
            finish()
        }
    }
}


