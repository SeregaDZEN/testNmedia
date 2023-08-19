package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.functions.reduceNumber


class MainActivity : AppCompatActivity() {

    val str = "userId"

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val post = Post(
            id = 1, "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "13 августа в 19:36",
            likes = 0,
            share = 0,
            views = 0,
            likedByMe = false

        )
//Сейчас уже должно быть отформатировано
        with(binding) {
            root.setOnClickListener {
                println("root")
            }

            avatar.setOnClickListener{
                println("avatar")
            }
            content.setOnClickListener{
                println("content")
            }
            menu.setOnClickListener{
                println("menu")
            }
           

            author.text = post.author
            published.text = post.published
            content.text = post.content
            textForLike.text = reduceNumber(post.likes)
            textForShare.text = reduceNumber(post.share)
            textForSee.text = post.views.toString()

            buttonLike.setImageResource(if (post.likedByMe) R.drawable.like_red else R.drawable.ic_like)


            binding.buttonLike.setOnClickListener {
                post.likedByMe = !post.likedByMe

                if (post.likedByMe) {
                    post.likes +=1999
                    val shortLike = reduceNumber(post.likes)
                    textForLike.text = shortLike
                    buttonLike.setImageResource(R.drawable.like_red)

                } else {
                    post.likes--
                    val shortLike = reduceNumber(post.likes)
                    textForLike.text = shortLike
                    buttonLike.setImageResource(R.drawable.ic_like)
                }
            }
            binding.buttonShare.setOnClickListener {
                post.share ++ // проверил с другими числами, всё ок
                val shortNumber = reduceNumber(post.share)
                textForShare.text = shortNumber
                // применил функцию сокращения

            }

        }


    }


}