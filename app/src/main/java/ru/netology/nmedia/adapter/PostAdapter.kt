package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.functions.reduceNumber

typealias OnClickListener = (post: Post) -> Unit

class PostsAdapter(
    private val onLikeListener: OnClickListener,
    private val onShareListener: OnClickListener
) :
    ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {


    //cоздаем класс кот-ый содержит в себе  информацию и разметку, и передаётся в RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onLikeListener, onShareListener)
    }

    // заполняаем элементы
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {

        val post = getItem(position)
        holder.bind(post)
    }

//  override fun getItemCount(): Int = list.size
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onLikeListener: OnClickListener,
    private val onShareListener: OnClickListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            textForLike.text = reduceNumber(post.likes)
            textForShare.text = reduceNumber(post.share)
            textForSee.text = post.views.toString()

            buttonLike.setImageResource(if (!post.likedByMe) R.drawable.ic_like else R.drawable.like_red)

            buttonLike.setOnClickListener {
                onLikeListener(post)
            }

            binding.buttonShare.setOnClickListener {
                onShareListener(post)
            }
        }
    }

}

// следит за элементами, когда обновляем список

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Post, newItem: Post) = oldItem == newItem
}




